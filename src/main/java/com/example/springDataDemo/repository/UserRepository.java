package com.example.springDataDemo.repository;

import com.example.springDataDemo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    @Override
    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);
}
