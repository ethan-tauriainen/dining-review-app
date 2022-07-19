package com.portfolio.diningreviewapp.repository;

import java.util.Optional;

import com.portfolio.diningreviewapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByDisplayName(String displayName);
}
