package com.luv2code.springbootlibrary.controller;
import com.luv2code.springbootlibrary.service.MessagesService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springbootlibrary.entity.Message;
import com.luv2code.springbootlibrary.service.Utils.ExtractJWT;

import requestmodels.AdminQuestionRequest;

import com.luv2code.springbootlibrary.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

	private MessagesService messagesService;
	
	public MessagesController(MessagesService messagesService) {
		this.messagesService=messagesService;
	}
	
	@PostMapping("/secure/add/message")
	public void postMessage(@RequestHeader(value="Authorization") String token,
							@RequestBody Message messageRequest) {
		String userEmail=ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		messagesService.postMessage(messageRequest, userEmail);
	}
	
	@GetMapping("/search/byUserEmail")
	public List<Message> getMessage(@RequestHeader(value="Authorization") String token){
		String userEmail=ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
		return messagesService.findMessage(userEmail);
	}
		
	@PutMapping("/secure/admin/message")	//api endpoint for admins only
	public void putMessage(@RequestHeader(value="Authorization") String token,
							@RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception{
		String userEmail=ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		String admin=ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
		if(admin==null || !admin.equals("admin")) {
			throw new Exception("Administration page only!");
		}
		messagesService.putMessage(adminQuestionRequest, userEmail);
	} 
	
}
