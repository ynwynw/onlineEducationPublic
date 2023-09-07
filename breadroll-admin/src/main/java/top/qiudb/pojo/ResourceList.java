package top.qiudb.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
/**
 * 资料表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceList {
    @Id
    private Integer resourceId;         //资料Id
    private String resourceName;        //资料名称
    private Integer breadCoin;          //所需花卷币
    private String fileType;            //文件类型
    private String fileSize;            //文件大小
    private String uploader;            //上传人
    private String fileUrl;             //文件url
    private String remark;              //备注
    private Boolean publishState;       //发布状态
    private Integer auditState;         //审核状态
    private Boolean deleteState;        //删除状态
}
