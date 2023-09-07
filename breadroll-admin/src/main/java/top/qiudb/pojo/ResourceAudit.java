package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
/**
 * 资料审核表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceAudit {
    @Id
    private Integer auditId;            //审核Id
    private Integer resourceId;         //资料Id
    private String resourceName;        //资料名称
    private Integer reviewerId;         //审核人Id
    private String reviewer;            //审核人
    private Integer publisherId;        //发布人Id
    private String publisher;           //发布人
    private Integer auditState;         //审核状态
    private Date auditTime;             //审核时间
    private Boolean deleteState;        //删除状态
    private String remark;              //备注
}
