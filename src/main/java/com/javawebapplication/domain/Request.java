package com.javawebapplication.domain;

import com.javawebapplication.enumeration.Status;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 1000)
    private String description;
    private String imageName;
    private Status status = Status.REQUEST_PENDING;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Request() {
    }

    public Request(String description, String imageName, User user) {
        this.description = description;
        this.imageName = imageName;
        this.user = user;
    }

    public Request(Request request) {
        this.id = request.getId();
        this.description = request.getDescription();
        this.imageName = request.getImageName();
        this.user = request.getUser();
        this.status = request.getStatus();
    }


    public String getImagePath() {
        if (imageName == null || id == null) return null;
        return "/requests_images/" + this.user.getLastname() + "_" + this.user.getFirstname() + "/" + imageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(this.description, request.getDescription()) && Objects.equals(this.imageName, request.getImageName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description, this.imageName);
    }

}
