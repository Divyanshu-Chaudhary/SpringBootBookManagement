package com.api.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.book.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    // @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(String email);

}
