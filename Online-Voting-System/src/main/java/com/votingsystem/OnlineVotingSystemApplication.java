package com.votingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(
		basePackages = "com.votingsystem",
		includeFilters = {
				@ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ANNOTATION,
						classes = {org.springframework.stereotype.Component.class,
								org.springframework.stereotype.Service.class,
								org.springframework.stereotype.Repository.class,
								org.springframework.web.bind.annotation.RestController.class})
		}
)

public class OnlineVotingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineVotingSystemApplication.class, args);
	}

}
