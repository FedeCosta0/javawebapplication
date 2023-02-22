package com.javawebapplication.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity @Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Request> requests = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Authority> authorities = new HashSet<>();

    public User() {
    }

    public User(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void addAuthority(String authority_level) {
        Authority authority = new Authority();
        authority.setAuthority(authority_level);
        authority.setUser(this);
        authorities.add(authority);
    }

    public void addRequest(Request request) {
        request.setUser(this);
        requests.add(request);
    }

    public void removeRequest(Request requestToBeDeleted) {
        requests.remove(requestToBeDeleted);
        requestToBeDeleted.setUser(null);
    }

    public Boolean isAdmin() {
        boolean result = Boolean.FALSE;
        for (Authority authority : authorities) {
            result = result || authority.getAuthority().equals("ROLE_ADMIN");
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User otherUser = (User) o;
        return Objects.equals(getEmail(), otherUser.getEmail());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

}
