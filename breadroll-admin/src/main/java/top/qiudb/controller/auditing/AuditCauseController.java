package top.qiudb.controller.auditing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.qiudb.pojo.*;
import top.qiudb.service.auditing.ArticleAuditService;
import top.qiudb.service.auditing.CourseAuditService;
import top.qiudb.service.auditing.ResourceAuditService;
import top.qiudb.service.auditing.SpecialAuditService;
import top.qiudb.service.course.ArticleService;
import top.qiudb.service.course.CourseService;
import top.qiudb.service.course.ResourceListService;
import top.qiudb.util.tools.ResultVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/auditCause")
@Api(tags="审核原因信息操作接口")
public class AuditCauseController {
    @Autowired
    private CourseAuditService courseAuditService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ResourceAuditService resourceAuditService;
    @Autowired
    private ResourceListService resourceListService;
    @Autowired
    private ArticleAuditService articleAuditService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SpecialAuditService specialAuditService;

    @GetMapping("/goToPassAudit")
    @ApiOperation("跳转到审核通过页面")
    public String goToPassAudit(String auditType,String auditId, Model model){
        model.addAttribute("auditId",auditId);
        model.addAttribute("auditType",auditType);
        return "auditing/pass-audit";
    }

    @GetMapping("/goToRefuseAudit")
    @ApiOperation("跳转到审核拒绝页面")
    public String goToRefuseAudit(String auditType,String auditId, Model model){
        model.addAttribute("auditId",auditId);
        model.addAttribute("auditType",auditType);
        return "auditing/refuse-audit";
    }

    @PostMapping("/addCourseAuditRemark")
    @ApiOperation("添加课程审核备注")
    @ResponseBody
    public ResultVO addCourseAuditRemark(int auditId, String remark, int auditState, HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager= (Manager) session.getAttribute("manager");
        CourseAudit courseAudit=new CourseAudit();
        courseAudit.setCourseId(auditId);
        courseAudit.setRemark(remark);
        courseAudit.setReviewerId(manager.getManagerId());
        courseAudit.setReviewer(manager.getManagerName());
        courseAudit.setAuditState(auditState);
        courseAudit.setAuditTime(new Date());
        //添加成功 或  拒绝 备注
        int n=courseAuditService.addCourseAuditRemark(courseAudit);
        if (remark.contains("拒绝原因")){
            if (n==1){
                //改变课程表中的审核状态
                int i=courseService.updateOFFAuditState(auditId,2);
                if (i==1){
                    return ResultVO.success("操作成功");
                }
                return ResultVO.error("操作失败");
            }
        }else{
            //改变课程审核表中的审核状态
            if (n==1){
                //改变课程表中的审核状态
                int i=courseService.updateOFFAuditState(auditId,1);
                if (i==1){
                    return ResultVO.success("操作成功");
                }
                return ResultVO.error("操作失败");
            }
        }
        return ResultVO.error("操作失败");
    }
    @PostMapping("/addResourceAuditRemark")
    @ApiOperation("添加资料审核备注")
    @ResponseBody
    public ResultVO addResourceAuditRemark(int auditId,String remark,int auditState,HttpServletRequest request){
        HttpSession session=request.getSession();
        Manager manager=(Manager) session.getAttribute("manager");
        ResourceAudit resourceAudit=new ResourceAudit();
        resourceAudit.setResourceId(auditId);
        resourceAudit.setRemark(remark);
        resourceAudit.setReviewer(manager.getManagerName());
        resourceAudit.setReviewerId(manager.getManagerId());
        resourceAudit.setAuditState(auditState);
        resourceAudit.setAuditTime(new Date());
        int n= resourceAuditService.addResourceAuditRemark(resourceAudit);
        if (remark.contains("拒绝原因")){
            if(n==1){
                //改变资料表中的审核状态
                int i=resourceListService.updateAuditState(auditId,2);
                if(i==1){
                    return ResultVO.success("操作成功");
                }
                return ResultVO.error("操作失败");
            }
        }else{
            //改变资料表中的审核状态
            if (n==1){
                int i=resourceListService.updateAuditState(auditId,1);
                if(i==1){
                    return ResultVO.success("操作成功");
                }
                return ResultVO.error("操作失败");
            }
        }
        return ResultVO.error("操作失败");
    }

    @PostMapping("/addArticleAuditRemark")
    @ApiOperation("添加文章审核备注")
    @ResponseBody
    public ResultVO addArticleAuditRemark(int auditId, String remark, int auditState, HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager=(Manager) session.getAttribute("manager");
        ArticleAudit articleAudit=new ArticleAudit();
        articleAudit.setArticleId(auditId);
        articleAudit.setRemark(remark);
        articleAudit.setReviewer(manager.getManagerName());
        articleAudit.setReviewerId(manager.getManagerId());
        articleAudit.setAuditState(auditState);
        articleAudit.setAuditTime(new Date());
        int n=articleAuditService.addArticleAuditRemark(articleAudit);
        if (remark.contains("拒绝原因")){
            if (n==1){
                //改变文章表中的审核状态
                int i=articleService.updateAuditState(auditId,2);
                if (i==1){
                    return ResultVO.success("操作成功");
                }
                return ResultVO.error("操作失败");
            }
        }else{
            //改变文章表中的审核状态
            if (n==1){
                //改变课程表中的审核状态
                int i=articleService.updateAuditState(auditId,1);
                if (i==1){
                    return ResultVO.success("操作成功");
                }
                return ResultVO.error("操作失败");
            }
        }
        return ResultVO.error("操作失败");
    }

    @PostMapping("/addSpecialAuditRemark")
    @ApiOperation("添加特训班审核备注")
    @ResponseBody
    public ResultVO addSpecialAuditRemark(int auditId, String remark, int auditState, HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager=(Manager) session.getAttribute("manager");
        Course course=courseService.queryByCourseId(auditId);
        SpecialAudit specialAudit=new SpecialAudit();
        specialAudit.setCourseId(auditId);
        specialAudit.setRemark(remark);
        specialAudit.setReviewer(manager.getManagerName());
        specialAudit.setReviewerId(manager.getManagerId());
        specialAudit.setAuditState(auditState);
        specialAudit.setAuditTime(new Date());
        int n= specialAuditService.addSpecialAuditRemark(specialAudit);
        if (remark.contains("拒绝原因")){
            if (n==1){
                //改变课程表中的审核状态
                int i=courseService.updateOFFAuditState(auditId,2);
                if (i==1){
                    return ResultVO.success("操作成功");
                }
                return ResultVO.error("操作失败");
            }
        }else{
            //改变课程审核表中的审核状态
            if (n==1){
                //改变课程表中的审核状态
                int i=courseService.updateOFFAuditState(auditId,1);
                if (i==1){
                    return ResultVO.success("操作成功");
                }
                return ResultVO.error("操作失败");
            }
        }
        return ResultVO.success("操作失败");
    }
}
