package fr.aka.supermarket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.aka.supermarket.service.ICheckoutService;

	

@SpringBootApplication
public class AkaSupermarketApplication {

	@Autowired
	private  ICheckoutService checkOutService;
	
	@PostConstruct
	public void init()  {
		checkOutService.perform();
	}
	
	public static void main(String[] args) throws RuntimeException {
			SpringApplication.run(AkaSupermarketApplication.class, args);
			
			System.exit(0);
		
	}

}
