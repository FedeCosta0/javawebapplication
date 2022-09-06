package com.javawebapplication.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Request {
    private Long id;
    private String description;
    private String imageName;
    private User user;
    private Boolean accepted;

    public Request() {
    }

    public Request(String description, String imageName, User user) {
        this.description = description;
        this.imageName = imageName;
        this.user = user;
        this.accepted = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 1000)
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

    @ManyToOne
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(getId(), request.getId()) && Objects.equals(getDescription(), request.getDescription()) && Objects.equals(getImageName(), request.getImageName()) && Objects.equals(getUser(), request.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getImageName(), getUser());
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", user=" + user +
                ", accepted=" + accepted +
                '}';
    }
}
