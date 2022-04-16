package org.feather.bilibili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-04-07 22:33
 **/
@SpringBootApplication
public class BiliBiliApplication {
    public static void main(String[] args) {
        ApplicationContext app=SpringApplication.run(BiliBiliApplication.class,args);
    }
}
