package com.example.re_knowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ServletComponentScan
@SpringBootApplication
public class ReKnowledgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReKnowledgeApplication.class, args);
	}
}
