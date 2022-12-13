package br.rn.sesed.sides.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SidesController {
	
	@Autowired
	BuildProperties buildProperties;
	
	
	@GetMapping("/version")
	public BuildProperties version() {
		
		return buildProperties;
	}	
	
}
