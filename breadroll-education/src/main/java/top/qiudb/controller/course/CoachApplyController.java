package top.qiudb.controller.course;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.qiudb.param.RetreatApplyParam;
import top.qiudb.pojo.course.CoachApply;
import top.qiudb.service.course.CoachApplyService;
import top.qiudb.util.tools.ResultVO;
import java.util.HashMap;
import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/21 8:58
 * @description 报名信息接口
 */
@RestController
@CrossOrigin
@RequestMapping("/course")
@Api(tags = "报名信息相关接口")
public class CoachApplyController {
    @Autowired
    CoachApplyService coachApplyService;

    // 获取用户报名信息
    @GetMapping("/coachApply/user")
    @ApiOperation("获取用户报名信息")
    public ResultVO queryCoachApply(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        int userId  = StpUtil.getLoginIdAsInt();
        coachApplyService.startedClasses();
        List<CoachApply> coachApplies = coachApplyService.queryCoachApplyByUserId(userId, pageNum, pageSize);
        if(coachApplies!=null){
            Integer total = coachApplyService.queryCountByUserId(userId);
            HashMap<String, Object> data = new HashMap<>();
            data.put("list",coachApplies);
            data.put("total",total);
            return ResultVO.success("报名信息查询成功",data);
        }
        return ResultVO.error("报名信息获取失败");
    }

    // 申请退课
    @PostMapping("/coachApply/retreat")
    @ApiOperation("申请退课")
    public ResultVO retreatApply(@RequestBody RetreatApplyParam retreatApplyParam) {
        Integer coachId = retreatApplyParam.coachId;
        CoachApply coachApply = coachApplyService.queryCoachApplyById(coachId);
        if(coachApply.getApplyState()==0){
            coachApply.setApplyState(1);
            Boolean isSuccess = coachApplyService.updateCoachApply(coachApply);
            if(isSuccess){
                return ResultVO.success("退课申请提交成功");
            }
            return ResultVO.error("退课申请提交失败");
        }
        return ResultVO.error("订单状态错误，请刷新页面后重试");
    }
}
