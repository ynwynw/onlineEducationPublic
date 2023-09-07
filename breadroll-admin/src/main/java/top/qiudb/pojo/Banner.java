package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Id
    private Integer bannerId;       //轮播图Id
    private String courseName;      //所对应课程名称
    private Integer courseId;       //所对应课程Id
    private String bannerUrl;       //宣传图url
    private Boolean bannerState;    //轮播图状态
    private Boolean deleteState;    //删除状态
}
