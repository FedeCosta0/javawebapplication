package com.javawebapplication.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 1000)
    private String description;
    private String imageName;
    private Status status = Status.REQUEST_PENDING;
    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    public Request() {
    }

    public Request(Long id, String description, String imageName, User user) {
        this.id = id;
        this.description = description;
        this.imageName = imageName;
        this.user = user;
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


    public Status getStatus() {
        return Status.valueOf(status.name());
    }
    public void setStatus(Status status) {
        this.status = Status.valueOf(status.name());
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
