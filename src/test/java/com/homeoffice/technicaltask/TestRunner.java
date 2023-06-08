package com.homeoffice.technicaltask;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.homeoffice.technicaltask"},
        plugin = {"pretty", "html:build/reports/tests/cucumber.html"},
        tags = "@all"
)
@CucumberContextConfiguration
@SpringBootTest()
@TestPropertySource({"classpath:application-${test.environment:dev}.properties"})
public class TestRunner {
}