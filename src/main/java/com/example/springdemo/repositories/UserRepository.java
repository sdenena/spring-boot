package com.example.springdemo.repositories;

import com.example.springdemo.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByIdAndStatusTrue(Long id);
    Optional<Users> findByUsernameIgnoreCaseOrEmailIgnoreCaseAndStatusTrue(String username, String email);
}
