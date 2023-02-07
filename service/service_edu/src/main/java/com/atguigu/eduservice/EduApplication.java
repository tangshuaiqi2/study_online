package com.atguigu.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@MapperScan(basePackages = "com.atguigu.eduservice.mapper")
@EnableDiscoveryClient
public class EduApplication {
	public static void main(String[] args) {
		SpringApplication.run(EduApplication.class, args);
	}
}
