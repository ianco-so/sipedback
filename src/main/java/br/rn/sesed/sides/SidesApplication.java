package br.rn.sesed.sides;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SidesApplication  extends SpringBootServletInitializer {
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(SidesApplication.class);
	}
	
	
	public static void main(String[] args) {
		log.info("========================================");
		log.info("NÃO UTILIZE O SPRIGBOOT NESTA APLICAÇÃO ");
		log.info("DESENVOLVIDA PARA EXECUÇÃO NO WILDFLY 17");
		log.info("Date: 2022-12-10");
		log.info("Author: emiliano.loiola@gmail.com");
		log.info("========================================");
		System.exit(0);
		SpringApplication.run(SidesApplication.class, args);
	}
	
	
}
