package top.qiudb.controller.auditing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.ResourceAudit;
import top.qiudb.service.auditing.ResourceAuditService;
import top.qiudb.util.tools.ResultVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resourceAudit")
@Api(tags = "资料审核信息操作接口")
public class ResourceAuditController {
    @Autowired
    private ResourceAuditService resourceAuditService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询资料审核信息")
    public ResultVO queryPageResourceAudit(@RequestParam int pageNum, @RequestParam int pageSize){
        List<ResourceAudit> pageAudit= resourceAuditService.pageResourceAudit(pageNum,pageSize);
        List<ResourceAudit> totalAudit= resourceAuditService.totalResourceAudit();
        Map<String,Object> data=new HashMap<>();
        data.put("list",pageAudit);
        data.put("total",totalAudit.size());
        if(totalAudit.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }
    @PostMapping("/searchAudit")
    @ResponseBody
    @ApiOperation("搜索资料审核信息")
    public ResultVO searchResourceAudit(@ApiParam("资料名称")String resourceName,
                                        @RequestParam int pageNum, @RequestParam int pageSize){
        ResourceAudit resourceAudit=new ResourceAudit();
        resourceAudit.setResourceName(resourceName);
        List<ResourceAudit> searchAudit=resourceAuditService.searchAudit(resourceAudit,pageNum,pageSize);
        List<ResourceAudit> totalSearchAudit=resourceAuditService.totalSearchAudit(resourceAudit);
        Map<String,Object> data=new HashMap<>();
        if(resourceName.trim().equals("")){
            List<ResourceAudit> totalAudit= resourceAuditService.totalResourceAudit();
            data.put("total",totalAudit.size());
        }else{
            data.put("total",totalSearchAudit.size());
        }
        data.put("list",searchAudit);
        if(searchAudit.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }

    @PostMapping("/refuseCause")
    @ApiOperation("查询拒绝原因")
    @ResponseBody
    public ResultVO queryRefuseCause(@ApiParam("资料Id") int resourceId){
        String remark= resourceAuditService.queryRefuseCause(resourceId);
        return ResultVO.success(remark);
    }
}
