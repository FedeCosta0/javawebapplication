package com.javawebapplication.repository;

import com.javawebapplication.domain.Request;
import com.javawebapplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByUser(User user);




}
