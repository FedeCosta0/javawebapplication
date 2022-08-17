package com.javawebapplication4.repositories;

import com.javawebapplication4.domain.Request;
import com.javawebapplication4.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository {

    @Query("select p from Request p"
            + " join fetch p.user"
            + " where p.id = :id")
    Optional<Request> findByIdWithUser(Long id);

    List<Request> findByUser(User user);
}
