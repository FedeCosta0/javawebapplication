package com.javawebapplication.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Request {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String description;
    private String imageName;
    @ManyToOne
    private User user;
    private Boolean accepted = Boolean.FALSE;

    public Request() {}

    public Request(String description, String imageName, User user) {
        this.description = description;
        this.imageName = imageName;
        this.user = user;
    }

    public Request(Request request) {
        this.description = request.getDescription();
        this.imageName = request.getImageName();
        this.user = request.getUser();
        this.accepted = request.getAccepted();
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

    public Boolean getAccepted() {
        return accepted;
    }
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Transient
    public String getImagePath() {
        if (imageName == null || id == null) return null;
        return "/requests_images/" + getUser().getLastname() + "_" + getUser().getFirstname() + "/" + imageName;
    }

    @Override
    @Transient
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(getDescription(), request.getDescription()) && Objects.equals(getImageName(), request.getImageName());
    }

    @Override
    @Transient
    public int hashCode() {
        return Objects.hash(getDescription(), getImageName());
    }

    @Transient
    public void erase() {
        if (this.user != null) {
            this.user.removeRequest(this);
            this.user = null;
        }
    }
}
