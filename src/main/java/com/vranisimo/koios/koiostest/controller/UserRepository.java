package com.vranisimo.koios.koiostest.controller;

import com.vranisimo.koios.koiostest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.id FROM User u WHERE u.email = ?1")
    Long findByEmail(String email);
}