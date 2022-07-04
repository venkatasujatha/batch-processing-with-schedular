package com.example.demo.Schedular;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredSchedular {
	@Scheduled(initialDelay = 10000,fixedDelay = 30000)
	public void markExpired()
	{
		System.out.println("hello world"+new Date());
	}

}
