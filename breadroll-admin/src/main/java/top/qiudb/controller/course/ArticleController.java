package top.qiudb.controller.course;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.*;
import top.qiudb.service.auditing.ArticleAuditService;
import top.qiudb.service.course.ArticleService;
import top.qiudb.service.course.CourseTypeService;
import top.qiudb.util.tools.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/article")
@Api(tags = "文章信息操作接口")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CourseTypeService courseTypeService;
    @Autowired
    private ArticleAuditService articleAuditService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询文章")
    public ResultVO pageListArticle(@RequestParam int pageNum, @RequestParam int pageSize){
        List<Article> pageArticle= articleService.queryPageArticle(pageNum,pageSize);
        List<Article> totalArticle=articleService.queryAllArticle();
        Map<String,Object> data=new HashMap<>();
        data.put("list",pageArticle);
        data.put("total",totalArticle.size());
        if(totalArticle.size()==1){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }

    @GetMapping("/goToEditArticle")
    @ApiOperation("跳转到添加/编辑文章")
    public String goToEditArticle(@ApiParam("文章Id") int articleId, Model model){
        List<CourseType> courseTypes= courseTypeService.queryOnlineType();
        if(articleId!=0){
            Article article= articleService.queryArticleById(articleId);
            model.addAttribute("article",article);
        }
        model.addAttribute("courseTypes",courseTypes);
        return "course/add-edit-article-list";
    }


    @PostMapping("/searchArticle")
    @ResponseBody
    @ApiOperation("搜索文章")
    public ResultVO searchArticle(@ApiParam("文章Id")String articleId,
                                  @ApiParam("文章标题")String articleTitle,
                                  @ApiParam("类别")String typeName,
                                  @ApiParam("发布时间")String publishTime,
                                  @RequestParam int pageNum, @RequestParam int pageSize) throws ParseException {
        Article article=new Article();
        if(articleId!=null && articleId.trim().length()!=0){
            article.setArticleId(Integer.parseInt(articleId));
        }
        article.setArticleTitle(articleTitle);
        article.setTypeName(typeName);
        if(publishTime!=null && publishTime.trim().length()!=0){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date date=simpleDateFormat.parse(publishTime);
            article.setPublishTime(date);
        }
        List<Article> searchArticle= articleService.searchArticle(article,pageNum,pageSize);
        List<Article> totalSearchArticle=articleService.totalSearchArticle(article);
        Map<String,Object> data=new HashMap<>();
        assert articleId != null;
        if(articleId.trim().length()!=0 && articleTitle.trim().equals("") && typeName.trim().equals("") && Objects.requireNonNull(publishTime).trim().equals("")){
            List<Article> totalArticle=articleService.queryAllArticle();
            data.put("total",totalArticle.size());
        }else{
            data.put("total",totalSearchArticle.size());
        }
        data.put("list",searchArticle);
        if(searchArticle.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }

    @GetMapping("/deleteArticle")
    @ResponseBody
    @ApiOperation("删除文章")
    public ResultVO deleteArticle(@ApiParam("文章Id")int articleId){
        int n=articleService.deleteArticle(articleId);
        if(n==1){
            return ResultVO.success("删除成功");
        }
        return ResultVO.error("删除失败");
    }

    @PostMapping("/addArticle")
    @ResponseBody
    @ApiOperation("/添加文章")
    public ResultVO addArticle(@ApiParam("文章信息")Article article, HttpServletRequest request){
        HttpSession session = request.getSession();
        Manager manager= (Manager) session.getAttribute("manager");
        article.setPublisher(manager.getManagerName());
        article.setPublisherId(manager.getManagerId());
        int n= articleService.addArticle(article);
        if(n==1){
            return ResultVO.success("添加成功");
        }
        return ResultVO.error("添加失败");
    }

    @PostMapping("/editArticle")
    @ResponseBody
    @ApiOperation("/修改文章")
    public ResultVO editArticle(@ApiParam("文章信息")Article article){
        int n= articleService.updateArticle(article);
        if(n==1){
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @GetMapping("/updatePublishState")
    @ResponseBody
    @ApiOperation("修改文章的发布状态")
    public ResultVO updatePublishState(@ApiParam("文章Id")int articleId,HttpServletRequest request){
        Manager manager=(Manager) request.getSession().getAttribute("manager");
        int n=articleService.updatePublishState(articleId,manager.getManagerName(), manager.getManagerId());
        Article article=articleService.queryArticleById(articleId);
        Boolean publishState=article.getPublishState();
        //添加文章审核信息
        ArticleAudit articleAudit=new ArticleAudit();
        articleAudit.setArticleId(article.getArticleId());
        articleAudit.setArticleTitle(article.getArticleTitle());
        articleAudit.setPublisher(article.getPublisher());
        articleAudit.setPublisherId(article.getPublisherId());
        if(n==1){
            //如果文章发布状态改变true，则向文章审核表中添加一条记录
            //如果为false,则关闭了文章
            if(publishState){
                int offArticle=articleService.updateAuditState(articleId,0);
                if (offArticle==1){
                    int flag=articleAuditService.addArticleAudit(articleAudit);
                    if(flag==1){
                        return ResultVO.success("修改成功");
                    }
                }
                return ResultVO.error("修改失败");
            }else{
                int i=articleService.updateAuditState(articleId,-1);
                if(i==1){
                    //课程审核表的审核状态应为  3
                    int j=articleAuditService.updateArticleAudit(articleId,3);
                    if (j==1){
                        return ResultVO.success("已关闭审核状态");
                    }
                }
                return ResultVO.error("关闭审核状态失败");
            }
        }
        return ResultVO.error("失败");
    }

    @PostMapping("/refuseCause")
    @ApiOperation("查询拒绝原因")
    @ResponseBody
    public ResultVO queryRefuseCause(int articleId){
        String remark=articleAuditService.queryRefuseCause(articleId);
        return ResultVO.success(remark);
    }
}
