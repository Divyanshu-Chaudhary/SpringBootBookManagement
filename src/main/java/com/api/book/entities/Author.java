package com.api.book.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authId;
    private String authName;

    @ManyToOne
    @JsonBackReference
    private List<Book> book;

    public Author() {
    }
    public Author(int authId, String authName) {
        this.authId = authId;
        this.authName = authName;
    }   
    public int getAuthId() {
        return authId;
    }
    public void setAuthId(int authId) {
        this.authId = authId;
    }
    public String getAuthName() {
        return authName;
    }    
    public void setAuthName(String authName) {
        this.authName = authName;
    }
    public Book getBook() {
        return (Book) book;
    }

    public void setBook(Book book) {
        this.book = (List<Book>) book;
    }
    @Override
    public String toString() {
        return "Author [authId=" + authId + ", authName=" + authName + "]";
    }


}
