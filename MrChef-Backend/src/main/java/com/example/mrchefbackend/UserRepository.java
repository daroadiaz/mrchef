package com.example.mrchefbackend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {    
    @Query("SELECT u FROM User u WHERE u.username = ?1 ORDER BY u.id ASC LIMIT 1")
    User findByUsername(String username);
}