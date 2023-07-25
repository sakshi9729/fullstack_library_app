package com.luv2code.springbootlibrary.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springbootlibrary.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
	//Page<History> findBooksByUserEmail(@Param("user_email") String userEmail, Pageable pageable);
	@Query("select h from History h where userEmail in :email")
	Page<History> findByUserEmail(@Param("email") String userEmail, Pageable pageable);
}
