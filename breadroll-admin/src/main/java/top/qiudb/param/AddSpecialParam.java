package top.qiudb.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSpecialParam {
    @Id
    private Integer courseId;      //课程ID
    private String courseName;     //课程名字
    private Integer typeId;        //类型ID
    private String typeName;       //类型名字
    private String description;     //课程描述
    private Integer teacherId;      //讲师ID
    private Boolean vipState;       //VIP状态
    private Boolean online;         //是否线上
    private String startTime;         //开课时间
    private String courseTime;      //课程时长
    private Date putTime;           //上架时间
    private Date updateTime;        //修改时间
    private String publisher;       //发布人
    private Integer publisherId;     //发布人ID
    private double price;           //课程价格
    private Boolean publishState;   //课程发布状态
    private Integer auditState;     //课程审核状态
    private Boolean deleteState;    //删除状态
    private Integer playCount;      //播放量
    private String coverUrl;        //课程封面
    private Boolean endFlag;        //到期标志
}
