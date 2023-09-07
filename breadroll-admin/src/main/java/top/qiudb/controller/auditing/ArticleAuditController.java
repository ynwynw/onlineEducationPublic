package top.qiudb.controller.auditing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.Article;
import top.qiudb.pojo.ArticleAudit;
import top.qiudb.service.auditing.ArticleAuditService;
import top.qiudb.service.course.ArticleService;
import top.qiudb.util.tools.ResultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/articleAudit")
@Api(tags = "文章审核信息操作接口")
public class ArticleAuditController {
    @Autowired
    private ArticleAuditService articleAuditService;
    @Autowired
    private ArticleService articleService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询文章审核信息")
    public ResultVO queryPageArticleAudit(@RequestParam int pageNum, @RequestParam int pageSize){
        List<ArticleAudit> pageAudit= articleAuditService.queryPageAudit(pageNum,pageSize);
        List<ArticleAudit> totalAudit= articleAuditService.totalAudit();
        Map<String,Object> data=new HashMap<>();
        data.put("list",pageAudit);
        data.put("total",totalAudit.size());
        if(totalAudit.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }

    @GetMapping("/goToArticleDetails")
    @ApiOperation("跳转到查看文章")
    public String goToArticleDetails(@ApiParam("文章Id")int articleId,@ApiParam("审核Id")int auditId, Model model){
        ArticleAudit articleAudit=articleAuditService.queryAuditById(auditId);
        int auditState=articleAudit.getAuditState();
        Article article=articleService.queryArticleById(articleId);
        model.addAttribute("article",article);
        model.addAttribute("auditState",auditState);
        return "auditing/article-details";
    }

    @PostMapping("/refuseCause")
    @ApiOperation("查询拒绝原因")
    @ResponseBody
    public ResultVO queryRefuseCause(int articleId){
        String remark=articleAuditService.queryRefuseCause(articleId);
        return ResultVO.success(remark);
    }

    @PostMapping("/searchAudit")
    @ApiOperation("搜索文章审核信息")
    @ResponseBody
    public ResultVO searchArticlAudit(@ApiParam("文章标题")String articleTitle,
                                      @RequestParam int pageNum, @RequestParam int pageSize){
        ArticleAudit articleAudit=new ArticleAudit();
        articleAudit.setArticleTitle(articleTitle);
        List<ArticleAudit> searchAudit=articleAuditService.searchArticleAudit(articleAudit,pageNum,pageSize);
        List<ArticleAudit> totalSearchAudit=articleAuditService.totalSearchArticleAudit(articleAudit);
        Map<String,Object> data=new HashMap<>();
        if(articleTitle.trim().equals("")){
            List<ArticleAudit> totalAudit= articleAuditService.totalAudit();
            data.put("total",totalAudit.size());
        }else{
            data.put("total",totalSearchAudit.size());
        }
        data.put("list",searchAudit);

        if(searchAudit.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("无查询结果",data);
    }
}
