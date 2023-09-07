package top.qiudb.pojo.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/16 8:14
 * @description 学习资料
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResourceList {
    @Id
    private Integer resourceId;
    private String resourceName;
    private Integer breadCoin;
    private String fileType;
    private String fileSize;
    private String uploader;
    private String fileUrl;
    private String remark;
    private Boolean publish_state;
    private Boolean auditState;
    private Boolean deleteState;
}
