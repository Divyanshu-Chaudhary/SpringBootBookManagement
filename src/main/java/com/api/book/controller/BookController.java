package com.api.book.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.book.entities.Book;
import com.api.book.services.BookService;



@RestController
public class BookController {

    // @RequestMapping(value="/books", method = RequestMethod.GET)
    // @ResponseBody  -> not required when we use @RestController
    // public String getBooks(){
    //     return "Book Data";
    // }
    
    // @GetMapping("/books") //New and fast and flexible way to handle GET request
    // public List<Book> getBooks(){
    //     Book b1 = new Book();
    //     b1.setId(1);
    //     b1.setTitle("Java");
    //     b1.setAuthor("Abc");
    //     b1.setDescription("Java Programming Language");
    //     b1.setPrice(99.99);
    //     Book b2 = new Book();
    //     b2.setId(2);
    //     b2.setTitle("C++");
    //     b2.setAuthor("Xyz");
    //     b2.setDescription("c++ Programming Language");
    //     b2.setPrice(90.99);
    //     // return b1;
    //     List<Book> list = new ArrayList<>();
    //     list.add(b1);
    //     list.add(b2);
    //     return list;
    // }
    
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> list = this.bookService.getAllBooks();
        if(list.size()<=0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
        }
        
        return ResponseEntity.of(Optional.of(list));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id){
        Book book = this.bookService.getBookById(id);
        if(book==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(book));
    } 

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book b) {
        Book b1 = null;
        try{
            b1 = this.bookService.addBook(b);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") int id){
        Book book = null;
        try{
            book = this.bookService.deleteBook(id);
            return ResponseEntity.ok(book);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("books/{id}")
    public ResponseEntity<Book> putMethodName(@RequestBody Book book, @PathVariable("id") int id){

        try{
            this.bookService.updateBook(id, book);
            return ResponseEntity.ok().body(book);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
    }
    
     
}
