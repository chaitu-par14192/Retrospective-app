package com.example.demo.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MainAPIControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void getAllRetrosTest() throws Exception 
	{
	  mvc.perform(get("/getRetros")
	  			.accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.[0].Name").exists())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.[0].Name").isNotEmpty())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.[0].Name").value("Retro1"))
	      .andReturn();
	}

}
