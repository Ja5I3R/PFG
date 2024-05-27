package com.pfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pfg.interfaces.IUser;

@SpringBootApplication
public class Demo3Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(Demo3Application.class, args);
	}

	@Autowired
	private IUser repository;
	
	@Override
	public void run(String... args) throws Exception {

	}
}
