package top.qiudb.pojo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataModel {
    private Integer VIPUserCount;       //VIP用户数量
    private Integer VIPTodayProfit;     //VIP今日收益
    private Integer coachTodayProfit;   //特训班今日收益
    private Integer totalProfit;        //总收益
}
