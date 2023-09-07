package top.qiudb.controller.course;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.*;
import top.qiudb.service.auditing.ResourceAuditService;
import top.qiudb.service.course.ResourceListService;
import top.qiudb.service.user.ManagerService;
import top.qiudb.util.tools.ResultVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resource")
@Api(tags="资料信息操作接口")
public class ResourceListController {
    @Autowired
    private ResourceListService resourceListService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ResourceAuditService resourceAuditService;
    @GetMapping("/pageList")
    @ApiOperation("分页查询所有资料")
    @ResponseBody
    public ResultVO queryPageListResource(@RequestParam int pageNum, @RequestParam int pageSize){
        List<ResourceList> pageResource= resourceListService.queryPageResourceList(pageNum,pageSize);
        List<ResourceList> totalResource=resourceListService.queryTotalResource();
        Map<String,Object> data=new HashMap<>();
        data.put("list",pageResource);
        data.put("total",totalResource.size());
        if(totalResource.size()==1){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }

    @PostMapping("/searchResource")
    @ResponseBody
    @ApiOperation("搜索资料")
    public ResultVO searchResource(@ApiParam("资料Id")String resourceId,
                                   @ApiParam("资料名称")String resourceName,
                                   @RequestParam int pageNum, @RequestParam int pageSize){
        ResourceList resourceList=new ResourceList();
        if(resourceId!=null&&resourceId.trim().length()!=0){
            resourceList.setResourceId(Integer.parseInt(resourceId));
        }
        resourceList.setResourceName(resourceName);
        List<ResourceList> resourceLists= resourceListService.searchResource(resourceList,pageNum,pageSize);
        List<ResourceList> totalResourceLists=resourceListService.totalSearchResource(resourceList);
        Map<String,Object> data=new HashMap<>();
        assert resourceId != null;
        if(resourceId.trim().equals("")&&resourceName.trim().equals("")){
            List<ResourceList> totalResource=resourceListService.queryTotalResource();
            data.put("total",totalResource.size());
        }else{
            data.put("total",totalResourceLists.size());
        }
        data.put("list",resourceLists);
        if(resourceLists.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }

    @GetMapping("/goToEditResource")
    @ApiOperation("跳转到添加/编辑资料")
    public String goToEditCourse(@ApiParam("资料Id") int resourceId, Model model){
        if(resourceId!=0){
            ResourceList resourceList=resourceListService.queryResourceById(resourceId);
            model.addAttribute("resource",resourceList);
        }
        return "course/add-edit-resource";
    }

    @GetMapping("/delResource")
    @ResponseBody
    @ApiOperation("删除资料")
    public ResultVO delResource(@ApiParam("资料ID")int resourceId){
        int n=resourceListService.deleteResourceById(resourceId);
        if(n==1){
            return ResultVO.success("删除成功",200);
        }else{
            return ResultVO.error("删除失败",500);
        }
    }

    @PostMapping("/addResource")
    @ResponseBody
    @ApiOperation("添加资料")
    public ResultVO addResourc(@ApiParam("资料信息")ResourceList resourceList, HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager=(Manager) session.getAttribute("manager");
        resourceList.setUploader(manager.getManagerName());
        int n=resourceListService.addResource(resourceList);
        if(n==1){
            return ResultVO.success("添加成功");
        }
        return ResultVO.error("添加失败");
    }

    @PostMapping("/editResource")
    @ResponseBody
    @ApiOperation("编辑资料")
    public ResultVO editResource(@ApiParam("资料信息")ResourceList resourceList){
        int n=resourceListService.updateResource(resourceList);
        if(n==1){
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @GetMapping("/updatePublishState")
    @ResponseBody
    @ApiOperation("修改资料的发布状态")
    public ResultVO updatePublishState(@ApiParam("资料Id")String resourceId,HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager=(Manager) session.getAttribute("manager");
        int n=resourceListService.updateResourceState(Integer.parseInt(resourceId),manager.getManagerName());
        ResourceList resourceList=resourceListService.queryResourceById(Integer.parseInt(resourceId));
        Boolean publishState=resourceList.getPublishState();
        //添加一条相应的资料审核信息
        ResourceAudit resourceAudit=new ResourceAudit();
        resourceAudit.setResourceId(resourceList.getResourceId());
        resourceAudit.setResourceName(resourceList.getResourceName());
        resourceAudit.setPublisher(resourceList.getUploader());
        Manager publishManager=managerService.queryManagerByName(resourceList.getUploader());
        resourceAudit.setPublisherId(publishManager.getManagerId());
        if(n==1){
            //如果资料发布状态改变true，则向资料审核表中添加一条记录
            //如果为false,则关闭了资料
            if(publishState){
                int offCourse= resourceListService.updateAuditState(Integer.parseInt(resourceId),0);
                if (offCourse==1){
                    int flag=resourceAuditService.addResourceAudit(resourceAudit);
                    if(flag==1){
                        return ResultVO.success("修改成功");
                    }
                }
                return ResultVO.error("修改失败");
            }else{
                int i=resourceListService.updateAuditState(Integer.parseInt(resourceId),-1);
                if(i==1){
                    //课程审核表的审核状态应为  3
                    int j=resourceAuditService.updateResourceAudit(Integer.parseInt(resourceId),3);
                    if (j==1){
                        return ResultVO.success("已关闭审核状态");
                    }
                }
                return ResultVO.error("关闭审核状态失败");
            }
        }
        return ResultVO.error("失败");
    }

    @PostMapping("/queryFileUrl")
    @ResponseBody
    @ApiOperation("查询文件路径")
    public ResultVO queryFileUrl(@ApiParam("资料Id")int resourceId){
        String fileUrl=resourceListService.queryFileUrlById(resourceId);
        Map<String,Object> data=new HashMap<>();
        data.put("url",fileUrl);
        return ResultVO.success("成功",data);
    }

    @PostMapping("/refuseCause")
    @ApiOperation("查询拒绝原因")
    @ResponseBody
    public ResultVO queryRefuseCause(@ApiParam("资料Id") int resourceId){
        String remark= resourceAuditService.queryRefuseCause(resourceId);
        return ResultVO.success(remark);
    }
}
