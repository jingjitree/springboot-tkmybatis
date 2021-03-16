package top.inson.springboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//开启注解事务管理
@EnableTransactionManagement
public class TkMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TkMybatisApplication.class, args);
    }


}
