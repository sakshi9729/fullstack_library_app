package com.luv2code.springbootlibrary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springbootlibrary.dao.MessageRepository;
import com.luv2code.springbootlibrary.entity.Message;

import requestmodels.AdminQuestionRequest;

@Service
@Transactional
public class MessagesService {
	private MessageRepository messageRepository;
	
	@Autowired
	public MessagesService(MessageRepository messageRepository) {
		this.messageRepository=messageRepository;
	}
	
	public void postMessage(Message messageRequest, String userEmail) {
		Message message=new Message(messageRequest.getTitle(),messageRequest.getQuestion());
		message.setUserEmail(userEmail);
		messageRepository.save(message);
	}
	
	public List<Message> findMessage(String userEmail){
		List<Message> msg = messageRepository.findMessages(userEmail);
		return msg;
	}
	
	public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception{
		Optional<Message> message=messageRepository.findById(adminQuestionRequest.getId());
		if(!message.isPresent()) {
			throw new Exception("Message not found");
		}
		message.get().setAdminEmail(userEmail);
		message.get().setResponse(adminQuestionRequest.getResponse());
		message.get().setClosed(true);
		messageRepository.save(message.get());
	}
}
