package com.ecom.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ecom.auth.entity.User;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);
}
