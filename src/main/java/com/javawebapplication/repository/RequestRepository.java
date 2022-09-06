package com.javawebapplication.repository;

import com.javawebapplication.domain.Request;
import com.javawebapplication.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    List<Request> findByUser(User user);
}
