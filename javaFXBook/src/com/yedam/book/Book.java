package com.yedam.book;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
//	도서명, 저자, 출판사, 가격
	private SimpleStringProperty name;
	private SimpleStringProperty writer;
	private SimpleStringProperty publisher;
	private SimpleIntegerProperty price;
	
	public Book(String name, String writer, String publisher,
			int price) {
		this.name = new SimpleStringProperty(name);
		this.writer = new SimpleStringProperty(writer);
		this.publisher = new SimpleStringProperty(publisher);
		this.price = new SimpleIntegerProperty(price);
	}
	
	public Book() {
		
	}
	
	public String getName() {
		return this.name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public String getWriter() {
		return this.writer.get();
	}
	public void setWriter(String writer) {
		this.writer.set(writer);
	}
	public String getPublisher() {
		return this.publisher.get();
	}
	public void setPublisher(String publisher) {
		this.publisher.set(publisher);
	}
	public int getPrice() {
		return this.price.get();
	}
	public void setPrice(int price) {
		this.price.set(price);
	}
}
