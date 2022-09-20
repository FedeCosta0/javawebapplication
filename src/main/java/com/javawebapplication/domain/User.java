package com.javawebapplication.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Request> requests = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Authority> authorities = new HashSet<>();

    public User() {}

    public User(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.requests = new HashSet<>();
        this.authorities = new HashSet<>();
    }

    public User(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.requests = user.getRequests();
        this.authorities = user.getAuthorities();
    }

    @Transient
    public void addAuthority(String authority_level) {
        Authority authority = new Authority();
        authority.setAuthority(authority_level);
        authority.setUser(this);
        authorities.add(authority);
    }

    @Transient
    public void addRequest(Request request) {
        this.requests.add(request);
        request.setUser(this);
    }

    @Transient
    public void removeRequest(Request requestTobeDeleted) {
        this.requests.remove(requestTobeDeleted);
    }

    @Transient
    public void eraseDependencies() {
        for (Authority auth : this.authorities) {
            auth.setUser(null);
            authorities.remove(auth);
        }
        for (Request req : this.requests) {
            req.setUser(null);
            requests.remove(req);
        }
    }

    @Transient
    public Boolean isAdmin() {
        Boolean result = Boolean.FALSE;
        for (Authority authority : authorities) {
            result = result || authority.isAdmin();
        }
        return result;
    }

    @Override @Transient
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getFirstname(), user.getFirstname()) && Objects.equals(getLastname(), user.getLastname());
    }

    @Override @Transient
    public int hashCode() {
        return Objects.hash(getEmail(), getFirstname(), getLastname());
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
