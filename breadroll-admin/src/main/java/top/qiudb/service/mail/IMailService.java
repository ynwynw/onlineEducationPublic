package top.qiudb.service.mail;
import java.util.Map;
/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/21 18:47
 * @description 描述
 */
public interface IMailService {
    /**
     * 发送简单邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    boolean sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    boolean sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送带模板的邮件
     * @param to 收件人
     * @param subject 主题
     * @param data 内容参数
     */
    boolean sendTemplateMail(String to, String subject,String template, Map<String,Object> data);
}
