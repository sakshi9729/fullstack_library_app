//Adding the service for checkout
package com.luv2code.springbootlibrary.service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.luv2code.springbootlibrary.dao.BookRepository;
import com.luv2code.springbootlibrary.dao.CheckoutRepository;
import com.luv2code.springbootlibrary.dao.HistoryRepository;
import com.luv2code.springbootlibrary.entity.Book;
import com.luv2code.springbootlibrary.entity.Checkout;
import com.luv2code.springbootlibrary.entity.History;
import com.luv2code.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;

import lombok.extern.java.Log;

@Service
@Transactional
public class BookService {
	
	private BookRepository bookRepository;
	private CheckoutRepository checkoutRepository;//constructor dependency injection
	private HistoryRepository historyRepository;
	
	public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository, HistoryRepository historyRepository) {
		this.bookRepository=bookRepository;
		this.checkoutRepository=checkoutRepository;
		this.historyRepository=historyRepository;
	}
	public Book checkoutBook(String userEmail, Long bookId) throws Exception{
		Optional<Book> book=bookRepository.findById(bookId);
		Checkout validateCheckout=checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
		//since we do not want a user to checkout a particular book more than once
		if(!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable()<=0) {
			throw new Exception("Book doesn't exist or already checked out by the user");
		}
		book.get().setCopiesAvailable(book.get().getCopiesAvailable()-1);
		bookRepository.save(book.get());
		//now create new record in the db
		int daysToAdd=7;
		Checkout checkout = new Checkout(
				userEmail,
				LocalDate.now().toString(),	//today's date
				LocalDate.now().plusDays(daysToAdd).toString(), //return date which is after 7 days
				book.get().getId()
		);
		checkoutRepository.save(checkout);
		return book.get();
		
	}	
	
	public boolean checkoutBookByUser(String userEmail, Long bookId) {
		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
		if(validateCheckout!=null)
			return true;
		else
			return false;
		
	}
	
	public int currentLoansCount(String userEmail) {
		return checkoutRepository.findBooksByUserEmail(userEmail).size();
	}
	
	public List<ShelfCurrentLoansResponse> currentLoans (String userEmail) throws Exception{
		List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();
		List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
		List<Long> bookIdList = new ArrayList<>();
		for(Checkout i:checkoutList) {
			bookIdList.add(i.getBookId());
		}
		List<Book> books=bookRepository.findBooksByBookIds(bookIdList);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Book book:books) {
			Optional<Checkout> checkout=checkoutList.stream().filter(x->x.getBookId() == book.getId()).findFirst();
			if(checkout.isPresent()) {
				Date d1=sdf.parse(checkout.get().getReturnDate());
				Date d2=sdf.parse(LocalDate.now().toString());
				TimeUnit time=TimeUnit.DAYS;
				long difference_In_Time=time.convert(d1.getTime()-d2.getTime(), TimeUnit.MILLISECONDS);
				shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int)difference_In_Time));
				
			}
		}
		return shelfCurrentLoansResponses;
	}
	
	public void returnBook(String userEmail, Long bookId) throws Exception{
		Optional<Book> book=bookRepository.findById(bookId);
		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
		if(!book.isPresent() || validateCheckout==null) {
			throw new Exception("Book does not exist or is checked out by the user");
		}
		book.get().setCopiesAvailable(book.get().getCopiesAvailable()+1);
		bookRepository.save(book.get());
		checkoutRepository.deleteById(validateCheckout.getId());
		History history=new History(
				userEmail,
				validateCheckout.getCheckoutDate(),
				LocalDate.now().toString(),
				book.get().getTitle(),
				book.get().getAuthor(),
				book.get().getDescription(),
				book.get().getImg()
		);
		historyRepository.save(history);
	}
	
	public void renewLoan(String userEmail, Long bookId) throws Exception{
		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
		if(validateCheckout==null){
			throw new Exception("Book doesn't exist or is not checked out by the user");
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d1=sdf.parse(validateCheckout.getReturnDate());
		Date d2=sdf.parse(LocalDate.now().toString());
		if(d1.compareTo(d2) > 0 || d1.compareTo(d2)==0) {
			validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
			checkoutRepository.save(validateCheckout);
		}
	}
	
}
