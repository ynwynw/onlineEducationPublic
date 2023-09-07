package top.qiudb.pojo.banner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/15 8:20
 * @description 轮播图表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Banner {
    @Id
    private Integer bannerId;
    private String courseName;
    private Integer courseId;
    private String bannerUrl;
    private Boolean bannerState;
    private Boolean deleteState;
}
