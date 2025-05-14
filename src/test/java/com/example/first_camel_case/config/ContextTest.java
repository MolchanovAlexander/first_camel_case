package com.example.first_camel_case.config;

import com.example.first_camel_case.model.SomeSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@SpringBootTest

public class ContextTest extends TestConfig{
    @Autowired
    ApplicationContext applicationContext;

    @Before
    public void setup() {
        System.out.println(applicationContext.getBeansOfType(SomeSource.class));
    }

    @Test
    public void dnoTest() {
        System.out.println(applicationContext.getBean("PostgreSQL") + "______");
    }
}
