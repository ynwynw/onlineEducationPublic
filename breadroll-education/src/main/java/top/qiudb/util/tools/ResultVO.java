package top.qiudb.util.tools;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Object data;
    public ResultVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultVO error(String message){
        return new ResultVO (500,message);
    }
    public static ResultVO error(String message,Object data){
        return new ResultVO (500,message,data);
    }
    public static ResultVO success(String message){
        return new ResultVO(200,message);
    }
    public static ResultVO success(String message,Object data){
        return new ResultVO(200,message,data);
    }

    @Override
    public String toString() {
        return "{" +
                "code :" + code +
                ", message:'" + message + '\'' +
                ", data:" + data +
                '}';
    }

}