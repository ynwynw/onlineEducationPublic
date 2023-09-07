package top.qiudb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.qiudb.mapper")	//指定Dao包
public class BreadrollAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BreadrollAdminApplication.class, args);
    }
}
