package br.rn.sesed.sides.core.modelmapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		Converter<Date, String> dateToString = new Converter<Date, String>() {
			  public String convert(MappingContext<Date, String> context) {
				  if(context.getSource() != null) {
					  Date date = context.getSource();
					  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:sss'Z'");
					  sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
					  String formatedDate = sdf.format(date);
					  return formatedDate;
					  
				  }else {
					  return "";
				  }
			  }
		};
		TypeMap<Date, String> typemap = modelMapper.createTypeMap(Date.class, String.class);
		typemap.setConverter(dateToString);
		return modelMapper;
	}
	
	
	

}
