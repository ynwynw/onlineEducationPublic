package top.qiudb.controller.vip;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.qiudb.pojo.vip.UserVip;
import top.qiudb.pojo.vip.Vip;
import top.qiudb.service.vip.UserVipService;
import top.qiudb.service.vip.VipService;
import top.qiudb.util.tools.ResultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/14 15:24
 * @description Vip详情介绍表
 */
@RestController
@CrossOrigin
@RequestMapping("/vip")
@Api(tags = "VIP详情接口")
public class VipController {
    @Autowired
    VipService vipService;
    @Autowired
    UserVipService userVipService;

    // 获取VIP信息
    @GetMapping("/info")
    @ApiOperation("查询VIP详情")
    public ResultVO queryVips() {
        List<Vip> vips = vipService.queryAllVip();
        return ResultVO.success("vip查询成功",vips);
    }

    // 获取VIP信息
    @GetMapping("/my")
    @ApiOperation("查询我的VIP")
    public ResultVO queryMyVip() {
        int userId  = StpUtil.getLoginIdAsInt();
        Vip vip = userVipService.queryVipByUserId(userId);
        UserVip userVip = userVipService.queryUserVipByUserId(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("isVip",false);
        if(vip!=null){
            data.put("isVip",true);
            data.put("vipName",vip.getVipName());
            data.put("vipIcon",vip.getVipIcon());
            data.put("expireDate",userVip.getExpireTime());
        }
        return ResultVO.success("vip查询成功",data);
    }

    // 根据VipId查询Vip信息
    @GetMapping("/id")
    @ApiOperation("查询我的VIP")
    public ResultVO queryVipById(Integer vipId) {
        Vip vip = vipService.queryVipById(vipId);
        return ResultVO.success("vip查询成功",vip);
    }
}
