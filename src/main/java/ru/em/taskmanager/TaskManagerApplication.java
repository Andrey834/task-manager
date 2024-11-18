package ru.em.taskmanager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.em.taskmanager.config.RsaProperty;

@SpringBootApplication
@EnableConfigurationProperties(RsaProperty.class)
@OpenAPIDefinition(info = @Info(
        title = "Task Management System API",
        version = "0.1a",
        description = "Система управления задачами",
        contact = @Contact(name = "Andrey M", email = "andry834@yandex.ru", url = "https://t.me/andrey86m"))
)
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}
