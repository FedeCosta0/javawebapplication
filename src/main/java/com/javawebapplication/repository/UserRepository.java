package com.javawebapplication.repository;

import com.javawebapplication.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.requests WHERE u.id = :#{#id}")
    Optional<User> findByIdWithRequests(@Param("id") Long id);
}
