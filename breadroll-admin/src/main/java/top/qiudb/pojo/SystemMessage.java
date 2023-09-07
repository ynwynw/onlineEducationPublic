package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
/**
 * 公告表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemMessage {
    @Id
    private Integer messageId;          //公告Id
    private String title;               //公告标题
    private String content;             //公告内容
    private Integer publisherId;        //发布人Id
    private String publisher;           //发布人
    private Date publishTime;           //发布时间
    private String url;                 //超链接
    private Boolean deleteState;        //删除状态

}
