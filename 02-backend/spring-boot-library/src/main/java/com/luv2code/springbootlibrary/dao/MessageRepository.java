package com.luv2code.springbootlibrary.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luv2code.springbootlibrary.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	//Page<Message> findByUserEmail(@Param("user_email") String userEmail, Pageable pageable);
	@Query("select o from Message o where userEmail in :email")
	//List<Message> findMessages(@Param("email") String userEmail);
	List<Message> findMessages(@Param("email") String userEmail);
	
	Page<Message> findByClosed(@Param("closed") boolean closed, Pageable pageable);
}
