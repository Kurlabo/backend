package com.kurlabo.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.config.location="
        + "classpath:application.properties,"
        + "classpath:jwts.yml")
public class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
