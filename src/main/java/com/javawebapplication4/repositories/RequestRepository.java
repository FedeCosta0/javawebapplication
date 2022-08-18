package com.javawebapplication4.repositories;

import com.javawebapplication4.domain.Request;
import com.javawebapplication4.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    @Query("select p from Request p"
            + " join fetch p.user"
            + " where p.id = :id")
    Optional<Request> findByIdWithUser(Long id);

    List<Request> findByUser(User user);
    Optional<Request> findByName(String name);
}
