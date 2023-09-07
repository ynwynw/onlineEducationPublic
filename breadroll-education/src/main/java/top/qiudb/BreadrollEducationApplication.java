package top.qiudb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("top.qiudb.mapper")	//指定Dao包
@ServletComponentScan
public class BreadrollEducationApplication {
    public static void main(String[] args) {
        SpringApplication.run(BreadrollEducationApplication.class, args);
    }
}
