/**
 * 
 */
package com.example.demo.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.controller.MainAPIController;
import com.example.demo.model.FeedBack;
import com.example.demo.model.FeedBackTypeEnum;
import com.example.demo.model.Retrospective;
import com.example.demo.repository.RetrospectivePaginationRepository;
import com.example.demo.repository.RetrospectiveRepo;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MainAPIControllerMockTest {

	@InjectMocks
	MainAPIController apiController;
	
	@Mock
	RetrospectiveRepo retroRepo;
	
	@Mock
	RetrospectivePaginationRepository retroPageRepo;
	
	@SuppressWarnings("unchecked")
	@Test
	void testgetRetrosAPICall() throws Exception{
		Retrospective retro = new Retrospective();
		Set<String> participantSet = new HashSet<>();
		participantSet.add("Chaitanya");
		participantSet.add("Aman");
		retro.setDate(LocalDate.now());
		retro.setName("Mock Retro");
		retro.setSummary("Summary");
		retro.setParticipants(participantSet);
		Map<String,FeedBack> feedbacks = new HashMap<>();
		feedbacks.put("item1", new FeedBack("item1", "Chaitanya", "body", FeedBackTypeEnum.Idea));
		retro.setFeedbacks(feedbacks);
		List<Retrospective> retrospectiveList = new ArrayList<>();
		retrospectiveList.add(retro);
		when(retroRepo.findAll(Sort.by("date"))).thenReturn(retrospectiveList);
		ResponseEntity<Object> response = apiController.getAllRetros(0, 0);
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(1, ((List<Retrospective>)response.getBody()).size());
	}
	
	@Test
	void testgetRetrosAPICall1() throws Exception{
		when(retroRepo.findAll(Sort.by("date"))).thenReturn(null);
		ResponseEntity<Object> response = apiController.getAllRetros(0, 0);
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertNull(response.getBody());
	}
	
	@Test
	void testgetRetrosAPICall2() throws Exception{
		when(retroRepo.findAll(Sort.by("date"))).thenThrow(EntityNotFoundException.class);
		ResponseEntity<Object> response = apiController.getAllRetros(0, 0);
		assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("No Retros Found",response.getBody().toString());
	}
	
}
