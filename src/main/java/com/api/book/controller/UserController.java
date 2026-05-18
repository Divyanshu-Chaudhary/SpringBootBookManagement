package com.api.book.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.api.book.dao.BookRepository;
import com.api.book.dao.UserRepository;
import com.api.book.entities.Book;
import com.api.book.entities.User;

import jakarta.validation.Valid;



@Controller
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepo;


    @GetMapping("/user")
    public String user(Model m, Principal principal){
        m.addAttribute("title", "User Management");
        m.addAttribute("activePage","user");
        String username = principal.getName();
        //get the user data
        User user = userRepository.findByEmail(username);
        m.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/user/book")
    public String userBook(Model m){

        //Adding books
        List<Book> books = bookRepo.findAll();
        m.addAttribute("books", books);

        m.addAttribute("title", "Book Management");
        m.addAttribute("activePage","user_book");
        return "user_book";
    }

    @GetMapping("/signup")
    public String signup(Model m){
        m.addAttribute("title", "Register User");
        m.addAttribute("activePage","signup");
        m.addAttribute("user", new User()); 
        return "signup";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model m) {
        try {

            if(result.hasErrors()){
                m.addAttribute("user", user);
                return "signup";
            }

            user.setRole("ROLE_USER");
            user.setFine(0.0);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            System.out.println(user);

            User res = this.userRepository.save(user);

            System.out.println("Saved");

            m.addAttribute("user", res);

            return "redirect:/login";
        } catch (Exception e) {
            m.addAttribute("message", "An error occurred while registering the user: " + e.getMessage());
            return "signup";
        }
    }
    

    @GetMapping("/login")
    public String login(Model m){
        m.addAttribute("title","Login");
        m.addAttribute("activePage","login");

        return "login";
    }

    @GetMapping("/user/borrow/{id}")
    public String borrowBook(@PathVariable int id, Principal p){

        Book book = bookRepo.findById(id).get();

        if(book.getQuantity() > 0 &&
        book.getSelectedBy() == null){

            book.setQuantity(book.getQuantity() - 1);

            book.setSelectedBy(p.getName());

            bookRepo.save(book);
        }

        return "redirect:/user/book";
    }

    @GetMapping("/user/return/{id}")
    public String returnBook(@PathVariable int id){

        Book book = bookRepo.findById(id).get();

        book.setQuantity(book.getQuantity() + 1);

        book.setSelectedBy(null);

        bookRepo.save(book);

        return "redirect:/user/book";
    }


}
