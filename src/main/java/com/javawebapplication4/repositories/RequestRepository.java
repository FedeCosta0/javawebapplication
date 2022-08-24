package com.javawebapplication4.repositories;

import com.javawebapplication4.domain.Request;
import com.javawebapplication4.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
    List<Request> findByUser(User user);
}
