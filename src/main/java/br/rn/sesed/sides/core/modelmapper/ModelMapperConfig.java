package br.rn.sesed.sides.core.modelmapper;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		return modelMapper;
	}
	
	Converter<Date, String> dateToString = new AbstractConverter<Date, String>() {
        @Override
        protected String convert(Date source) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String dateFormated =  format.toString();	            
            return dateFormated;
        }
	};
	

}
