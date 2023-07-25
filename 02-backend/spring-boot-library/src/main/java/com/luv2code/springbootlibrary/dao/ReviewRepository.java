package com.luv2code.springbootlibrary.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luv2code.springbootlibrary.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findByBookId(@Param("bookId") Long bookId, Pageable pageable);
	Review findByUserEmailAndBookId(String userEmail, Long bookId);
	
	@Modifying
	@Query("DELETE FROM Review r WHERE r.bookId = :bookId")
	void deleteAllByBookId(@Param("bookId") Long bookId);
}
