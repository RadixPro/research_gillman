package nl.nvwoa.gillman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = "nl.nvwoa.gillman")
public class Gillman2Application {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Gillman2Application.class, args);
        Frontend frontend = ctx.getBean(Frontend.class);
        frontend.handleTask(args);
    }
}

