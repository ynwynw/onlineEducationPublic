package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
/**
 * 文章表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    private Integer articleId;          //文章Id
    private String articleTitle;        //标题
    private Integer readingCount;       //阅读量
    private Date publishTime;           //发布时间
    private String text;                //内容
    private String coverUrl;            //封面url
    private String typeName;            //分类
    private Boolean publishState;       //发布状态
    private Integer auditState;         //审核状态
    private String reprintUrl;          //转载链接
    private String publisher;           //发布人
    private Integer publisherId;        //发布人Id
    private Boolean deleteState;        //删除状态
}
