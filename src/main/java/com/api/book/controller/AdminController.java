package com.api.book.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.book.dao.BookRepository;
import com.api.book.dao.UserRepository;
import com.api.book.entities.Author;
import com.api.book.entities.Book;
import com.api.book.entities.User;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepo;


    @RequestMapping("/index")
    public String dashboard(Model m,Principal principal){
        m.addAttribute("title", "Admin Dashboard");
        m.addAttribute("activePage","dashboard");
        String username = principal.getName();
        //get the user data
        User user = userRepository.findByEmail(username);
        m.addAttribute("user", user);

        List<User> users = userRepository.findAll();
        m.addAttribute("users", users);
        

        return "admin_dashboard"; 
    }

     @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable Integer id){
        bookRepo.deleteById(id);
        return "redirect:/user/book";
    }

    @GetMapping("/updateBook/{id}")
    public String updateBook(@PathVariable Integer id, Model m){
        Book book = bookRepo.findById(id).get();

        m.addAttribute("book", book);
        return "update_book";
    }

    @GetMapping("/addBook")
    public String addBook(Model m){
        m.addAttribute("title", "Add Book");
        m.addAttribute("activePage","add_book");
        m.addAttribute("book", new Book());

        return "add_book";
    }

    @PostMapping("/saveBook")
    public String saveBook(@RequestParam String title,
        @RequestParam String authName,
        @RequestParam double price,
        @RequestParam String description){
        Author author = new Author();
        author.setAuthName(authName);

        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        book.setDescription(description);
        // IMPORTANT
        book.setAuthor(author);
        bookRepo.save(book);
        return "redirect:/user/book";
    }

    @PostMapping("/updateBook")
    public String updateBook(
            @RequestParam int id,
            @RequestParam String title,
            @RequestParam String authName,
            @RequestParam double price,
            @RequestParam String description){

        Book book = bookRepo.findById(id).get();
        book.setTitle(title);
        book.setPrice(price);
        book.setDescription(description);
        // Update Author
        Author author = book.getAuthor();
        if(author == null){
            author = new Author();
        }
        author.setAuthName(authName);
        book.setAuthor(author);
        bookRepo.save(book);

        return "redirect:/user/book";
    }
    
}
