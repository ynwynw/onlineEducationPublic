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
    static {
        Properties properties=new Properties();
        InputStream inputStream=PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("config.properties was not found");
        }
        uploadUrl = System.getProperty("user.dir")+"/upload/";
        address = (String) properties.get("address");
    }

    public static String getUploadUrl() {
        return uploadUrl;
    }

    public static String getAddress() {
        return address;
    }

}