package com.javawebapplication4.domain;

import javax.persistence.*;

@Entity
@Table(name = "requests")
public class Request {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private String imagePath; //Field not stored in database (getter method annotated @Transient)
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    @Transient
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagesPath) {
        this.imagePath = imagesPath;
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

}
