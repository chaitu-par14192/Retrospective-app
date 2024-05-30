package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CreateRetroModel;
import com.example.demo.model.FeedBack;
import com.example.demo.model.FeedbackModel;
import com.example.demo.model.Retrospective;
import com.example.demo.repository.RetrospectivePaginationRepository;
import com.example.demo.repository.RetrospectiveRepo;

@RestController
public class MainAPIController {

	@Autowired
	RetrospectiveRepo retroRepo;
	
	@Autowired
	RetrospectivePaginationRepository retroPagingRepo;
	
	@GetMapping("getRetros")
	public ResponseEntity<Object> getAllRetros(@RequestParam(required = false,defaultValue = "0") int pageSize,
			@RequestParam(required = false,defaultValue = "0") int pageNo){
		try {
			if(pageSize>0) {
				Pageable pageRequest = PageRequest.of(pageNo<0?0:pageNo, pageSize); 
				Page<Retrospective> retroList = retroPagingRepo.findAll(pageRequest);
				return new ResponseEntity<Object>(retroList, HttpStatus.OK);
			}else {
				List<Retrospective> retroList = retroRepo.findAll(Sort.by("date"));
				return new ResponseEntity<Object>(retroList, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>("No Retros Found", HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/createRetro")
	public ResponseEntity<String> createRetro(@RequestBody CreateRetroModel createRetro) {
		
		Retrospective newRetro = new Retrospective();
		newRetro.setName(createRetro.getName());
		newRetro.setSummary(createRetro.getSummary());
		if(createRetro.getDate()==null) {
			return new ResponseEntity<String>("Date of Retrospective is mandatory to create Retrospective",HttpStatus.BAD_REQUEST);
		}else {
			newRetro.setDate(createRetro.getDate());
		}
		if(createRetro.getParticipants()!=null && createRetro.getParticipants().size()>1) {
			newRetro.setParticipants(createRetro.getParticipants());
		}else {
			return new ResponseEntity<String>("Atleast Two partcipants of Retrospective is mandatory to create a new Retrospective",HttpStatus.BAD_REQUEST);
		}
		try {
			retroRepo.save(newRetro);
			return new ResponseEntity<String>("Retrospective Created Successfully",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("Retrospective Creation unsuccessfull",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path ="getRetro/{retroName}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Object> getSpecifcRetro(@PathVariable String retroName){
		try {
			Retrospective retro =  retroRepo.findById(retroName).get();
			return new ResponseEntity<Object>(retro,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Restrospective Not Found",HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("addFeedback/{retroName}")
	public ResponseEntity<String> addFeedBack(@PathVariable String retroName,@RequestBody FeedbackModel feedbackmodel){
		try {
			
			Retrospective retro =  retroRepo.findById(retroName).get();
			String itemNum= "Item"+(retro.getFeedbacks().keySet().size()+1);
			FeedBack feedback = new FeedBack(itemNum,feedbackmodel.Name(),feedbackmodel.Body(),feedbackmodel.FeedbackType());
			retro.getFeedbacks().put(itemNum, feedback);
			retroRepo.save(retro);
			return new ResponseEntity<String>("Feedback Added Successfully",HttpStatus.OK);
		} catch (NoSuchElementException e) {
			System.out.println("Retro Not Found");
			return new ResponseEntity<String>("Restrospective Not Found",HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("updateFeedback/{retroName}/{itemName}")
	public ResponseEntity<String> updateFeedback(@PathVariable String retroName,@PathVariable String itemName,@RequestBody FeedbackModel feedbackmodel){
		try {
			Retrospective retro =  retroRepo.findById(retroName).get();
			FeedBack item = retro.getFeedbacks().get(itemName);
			System.out.println(feedbackmodel);
			if(item == null)
				throw new NoSuchElementException("retrospective Item Not found");
			item.setBody(feedbackmodel.Body());
			item.setFeedBackType(feedbackmodel.FeedbackType());
			retro.getFeedbacks().put(itemName, item);
			retroRepo.save(retro);
			return new ResponseEntity<String>("Feedback Updated Successfully",HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<String>(e.getMessage()==null?"Restrospective Not Found":e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "getRetrosByDate",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Object> getRetrosByDate(@RequestParam(required = false,defaultValue = "0") int pageSize,
			@RequestParam(required = false,defaultValue = "0") int pageNo,@RequestParam String date){
		try {
			LocalDate date1 = LocalDate.parse(date);
			if(pageSize>0) {
				Pageable pageRequest = PageRequest.of(pageNo<0?0:pageNo, pageSize); 
				List<Retrospective> retroList = retroPagingRepo.findAllByDate(date1,pageRequest);
				return new ResponseEntity<Object>(retroList, HttpStatus.OK);
			}else {
				List<Retrospective> retroList = retroRepo.findAllByDate(date1);
				return new ResponseEntity<Object>(retroList, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>("No Retros Found", HttpStatus.NO_CONTENT);
		}
	}
	
}
