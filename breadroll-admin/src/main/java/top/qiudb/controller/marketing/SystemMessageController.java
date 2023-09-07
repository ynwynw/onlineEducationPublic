package top.qiudb.controller.marketing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.controller.loginInfo.LoginController;
import top.qiudb.pojo.*;
import top.qiudb.service.course.ArticleService;
import top.qiudb.service.course.CourseService;
import top.qiudb.service.course.ResourceListService;
import top.qiudb.service.marketing.SystemMessageService;
import top.qiudb.util.tools.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/message")
@Api(tags="系统公告消息操作接口")
public class SystemMessageController {
    @Autowired
    private SystemMessageService systemMessageService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ResourceListService resourceListService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询所有公告")
    public ResultVO queryPageList(@RequestParam int pageNum, @RequestParam int pageSize){
        List<SystemMessage> pageMessage=systemMessageService.queryPageMessage(pageNum,pageSize);
        List<SystemMessage> totalMessage=systemMessageService.queryTotalMessage();
        Map<String,Object> data=new HashMap<>();
        data.put("total",totalMessage.size());
        data.put("list",pageMessage);
        return ResultVO.success("查询成功",data);
    }

    @GetMapping("/goToEditMessage")
    @ApiOperation("跳转到添加/编辑公告")
    public String goToEditMessage(@ApiParam("公告Id") int messageId, Model model){
        //查询所有发布且可用的线上课程
        List<Course> courses=courseService.queryPublishedAuditedCourse();
        //查询所有文章
        List<Article> articles= articleService.queryAllPublishAuditArticle();
        //查询所有资料
        List<ResourceList> resourceLists= resourceListService.queryAllPublishAuditResource();
        model.addAttribute("courses",courses);
        model.addAttribute("articles",articles);
        model.addAttribute("resourceLists",resourceLists);
        //根据公告Id查询相应的公告
        if(messageId!=0){
            SystemMessage systemMessage=systemMessageService.queryMessageById(messageId);
            model.addAttribute("message",systemMessage);
            //获取url  并区分出第一个url和第二个url
            String messageUrl=systemMessage.getUrl();
            String oneUrl=messageUrl;
            String twoUrl;
            int index=messageUrl.indexOf("=");
            if(index>0){
                oneUrl=messageUrl.substring(0,index+1);
                if (index!=messageUrl.length()-1){
                    twoUrl=messageUrl.substring(index+1);
                    model.addAttribute("twoUrl",twoUrl);
                }
            }
            model.addAttribute("oneUrl",oneUrl);
        }
        return "marketing/add-edit-message";
    }

    @PostMapping("/addMessage")
    @ResponseBody
    @ApiOperation("添加公告")
    public ResultVO addMessage(SystemMessage systemMessage, HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager= (Manager) session.getAttribute("manager");
        systemMessage.setPublisher(manager.getManagerName());
        systemMessage.setPublisherId(manager.getManagerId());
        int n= systemMessageService.addSystemMessage(systemMessage);
        if(n==1){
            return ResultVO.success("发布成功");
        }
        return ResultVO.error("发布失败");
    }

    @PostMapping("/editMessage")
    @ResponseBody
    @ApiOperation("编辑公告")
    public ResultVO editMessage(SystemMessage systemMessage,HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager=(Manager) session.getAttribute("manager");
        systemMessage.setPublisher(manager.getManagerName());
        systemMessage.setPublisherId(manager.getManagerId());
        int n= systemMessageService.updateSystemMessage(systemMessage);
        if(n==1){
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @PostMapping("/searchMessage")
    @ResponseBody
    @ApiOperation("搜索公告")
    public ResultVO selectOnlineCourse(@ApiParam("公告标题")String title,
                                       @ApiParam("发布日期") String publishTime,
                                       @RequestParam int pageNum, @RequestParam int pageSize) throws ParseException {
        SystemMessage systemMessage=new SystemMessage();
        systemMessage.setTitle(title);
        if (publishTime!=null && publishTime.trim().length()!=0){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date date=simpleDateFormat.parse(publishTime);
            systemMessage.setPublishTime(date);
        }
        List<SystemMessage> searchMessage= systemMessageService.searchSystemMessage(systemMessage,pageNum,pageSize);
        List<SystemMessage> totalSearchMessage= systemMessageService.totalSearchSystemMessage(systemMessage);
        Map<String,Object> data=new HashMap<>();
        if(title.trim().equals("") && Objects.requireNonNull(publishTime).trim().equals("")){
            List<SystemMessage> totalMessage=systemMessageService.queryTotalMessage();
            data.put("total",totalMessage.size());
        }else{
            data.put("total",totalSearchMessage.size());
        }
        data.put("list",searchMessage);
        if(searchMessage.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }

    @GetMapping("/deleteMessage")
    @ResponseBody
    @ApiOperation("删除公告")
    public ResultVO deleteMessage(@ApiParam("公告Id")int messageId){
        int n=systemMessageService.deleteSystemMessage(messageId);
        if (n==1){
            return ResultVO.success("删除成功");
        }
        return ResultVO.error("删除失败");
    }
}
