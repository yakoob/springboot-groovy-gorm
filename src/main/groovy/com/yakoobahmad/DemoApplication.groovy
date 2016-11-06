package com.yakoobahmad

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class DemoApplication {

	static void main(String[] args) {
		SpringApplication.run DemoApplication, args

		Account.withNewSession {
			new Account(name: "koob", status: Account.Status.ACTIVE, created: new Date()).save()
			new Account(name: "dude", status: Account.Status.ACTIVE, created: new Date()).save()
		}

	}
}
