package ru.em.taskmanager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskManagerApplicationTests {

    @Test
    void contextLoads() {
        TaskManagerApplication.main(new String[]{});
    }
}
