package com.treee.learnspringboot01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class Learnspringboot01Application {

	public static void main(String[] args) {
		SpringApplication.run(Learnspringboot01Application.class, args);
//		SpringApplicationBuilder builder = new SpringApplicationBuilder(Learnspringboot01Application.class);
//		builder.bannerMode(Banner.Mode.OFF).run(args);
	}
}
