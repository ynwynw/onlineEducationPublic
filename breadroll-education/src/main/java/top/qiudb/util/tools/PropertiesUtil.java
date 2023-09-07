package top.qiudb.util.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/1/1 10:01
 * @description 读取配置文件信息
 */
public class PropertiesUtil {
    private static final String uploadUrl;
    private static final String address;
    private static final String adminAddress;
    private static final String callBack;
    private static final String adminPath;
    static {
        Properties properties=new Properties();
        InputStream inputStream= PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("config.properties was not found");
        }
        uploadUrl = System.getProperty("user.dir")+"/upload/";
        address = (String) properties.get("address");
        adminAddress = (String) properties.get("adminAddress");
        callBack = (String) properties.get("callBack");
        adminPath = (String) properties.get("adminPath");
    }

    public static String getUploadUrl() {
        return uploadUrl;
    }

    public static String getAddress() {
        return address;
    }

    public static String getAdminAddress() {
        return adminAddress;
    }

    public static String getCallBack() {
        return callBack;
    }

    public static String getAdminPath() {
        return adminPath;
    }
}