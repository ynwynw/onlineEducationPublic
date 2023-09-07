package top.qiudb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
/**
 * Vip表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vip {
    @Id
    private Integer vipId;          //会员Id
    private String vipName;         //会员名称
    private String vipMark;         //会员标识
    private String vipIcon;         //会员图标
    private Integer price;          //价格
    private Integer timeLength;     //会员时长
    private Integer breadCoin;      //所赠送的花卷币
    private Boolean vipState;       //VIP状态
    private Boolean deleteState;    //删除状态
}
