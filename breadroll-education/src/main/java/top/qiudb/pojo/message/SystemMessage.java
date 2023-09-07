package top.qiudb.pojo.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/12 22:02
 * @description 系统消息
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemMessage {
    @Id
    private Integer messageId;
    private String title;
    private String content;
    private Integer publisherId;
    private String publisher;
    private Date publishTime;
    private String url;
    private Boolean deleteState;
}
