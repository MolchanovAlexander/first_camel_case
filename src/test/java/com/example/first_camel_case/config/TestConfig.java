package com.example.first_camel_case.config;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/1-context.xml"})
public abstract class TestConfig {
    public int uuu = 0;
}
