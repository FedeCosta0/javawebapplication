package com.javawebapplication4.domain;

import javax.persistence.*;

@Entity
public class Request {
    private Long id;
    private String name;
    private String image;
    private String description;
    private User user;
    private Boolean accepted;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    @Column(length = 1000)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAccepted() {
        return accepted;
    }
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Transient
    public String getPhotosImagePath() {
        if (image == null || id == null) return null;

        return "/requests_images/"+ this.getUser().getLastname() + "_" + this.getName() + "/" + image;
    }
}
