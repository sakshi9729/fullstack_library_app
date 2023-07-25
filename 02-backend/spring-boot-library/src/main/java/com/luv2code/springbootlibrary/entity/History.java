package com.luv2code.springbootlibrary.entity;

import java.util.Objects;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name="History")
@Data
public class History {
	public History() {
		
	}
	public History(String userEmail, String checkoutDate, String returnedDate, String title,
					String author, String description, String img) {
		this.userEmail=userEmail;
		this.checkoutDate=checkoutDate;
		this.returnedDate=returnedDate;
		this.title=title;
		this.author=author;
		this.description=description;
		this.img=img;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="user_email")
	private String userEmail;
	
	@Column(name="checkout_date")
	private String checkoutDate;
	
	@Column(name="returned_date")
	private String returnedDate;
	
	@Column(name="title")
	private String title;
	
	@Column(name="author")
	private String author;
	
	@Column(name="description")
	private String description;
	
	@Column(name="img")
	private String img;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public String getReturnedDate() {
		return returnedDate;
	}
	public void setReturnedDate(String returnedDate) {
		this.returnedDate = returnedDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "History [id=" + id + ", userEmail=" + userEmail + ", checkoutDate=" + checkoutDate + ", returnedDate="
				+ returnedDate + ", title=" + title + ", author=" + author + ", description=" + description + ", img="
				+ img + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(author, checkoutDate, description, id, img, returnedDate, title, userEmail);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		History other = (History) obj;
		return Objects.equals(author, other.author) && Objects.equals(checkoutDate, other.checkoutDate)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(img, other.img) && Objects.equals(returnedDate, other.returnedDate)
				&& Objects.equals(title, other.title) && Objects.equals(userEmail, other.userEmail);
	}
	
	
}
