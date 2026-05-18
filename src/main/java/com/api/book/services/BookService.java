package com.api.book.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.book.dao.BookRepository;
import com.api.book.entities.Book;

@Component
public class BookService {

    //Can be considered as fake database 
    // private static List<Book> list = new ArrayList<>();
    // static{
    //     list.add(new Book(1, "Java", "Abc", "Java Programming Language", 99.99));
    //     list.add(new Book(2, "C++", "Xyz", "c++ Programming Language", 90.99));
    //     list.add(new Book(3, "Python", "Pqr", "Python Programming Language", 80.99));
    //     list.add(new Book(4, "JavaScript", "Def", "JavaScript Programming Language", 70.99));
    //     list.add(new Book(5, "C#", "Ghi", "C# Programming Language", 60.99));
    //     list.add(new Book(6, "Ruby", "Jkl", "Ruby Programming Language", 50.99));
    //     list.add(new Book(7, "Go", "Mno", "Go Programming Language", 40.99));
    // }

    @Autowired
    private BookRepository bookRepository;

    // get all books
    public List<Book> getAllBooks(){
        List<Book> list = (List<Book>)this.bookRepository.findAll();
        return list;
    }

    // get single book by id
    public Book getBookById(int id){
        Book book = null;
        try{
            book = this.bookRepository.findById(id);
            // for(Book b : list){
            //     if(b.getId() == id){
            //         book = b;
            //         break;
            //     }
            // }
            // book = list.stream().filter(e -> e.getId() == id).findFirst().get();
        }catch(Exception e){
            // System.out.println(e);
            e.printStackTrace();
        }

        return book;
    }

    public Book addBook(Book b){
        Book res = bookRepository.save(b);
        return res;
    }

    public Book deleteBook(int id){
        // Book book =null;
        // for(Book b: list){
        //     if(b.getId() == id){
        //         book=b;
        //         list.remove(b);
        //         break;
        //     }
        // }
        Book  book = bookRepository.findById(id);
        bookRepository.delete(book);
        return book;
    }

    public Book updateBook(int id, Book book){
        // Book bookToUpdate = null;
        // for(Book b: list){
        //     if(b.getId() == id){
        //         bookToUpdate =b;
        //         break;
        //     }
        // }
        // bookToUpdate.setTitle(book.getTitle());
        // bookToUpdate.setAuthor(book.getAuthor());
        // bookToUpdate.setDescription(book.getDescription());
        // bookToUpdate.setPrice(book.getPrice());
        // return book;
        book.setId(id);
        bookRepository.save(book);
        return book;
    }


}
