package top.qiudb.exception;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.qiudb.util.tools.ResultVO;


@ControllerAdvice
class GlobalExceptionHandler {

    // 全局异常拦截（拦截项目中的NotLoginException异常）
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public ResultVO handlerNotLoginException(NotLoginException nle) {
        // 打印堆栈，以供调试
        //nle.printStackTrace();
        // 判断场景值，定制化异常信息
        String message;
        switch (nle.getType()) {
            case NotLoginException.NOT_TOKEN:
                message = "未提供token";
                break;
            case NotLoginException.INVALID_TOKEN:
                message = "token无效";
                break;
            case NotLoginException.TOKEN_TIMEOUT:
                message = "token已过期";
                break;
            case NotLoginException.BE_REPLACED:
                message = "token已被顶下线";
                return new ResultVO(495,message);
            case NotLoginException.KICK_OUT:
                message = "token已被踢下线";
                break;
            default:
                message = "当前会话未登录";
                break;
        }

        // 返回给前端
        return new ResultVO(490,message);
    }

    @ExceptionHandler(DisableLoginException.class)
    @ResponseBody
    public ResultVO handlerDisableLoginException() {
        // 返回给前端
        return new ResultVO(500,"此账号已被封禁");
    }
}