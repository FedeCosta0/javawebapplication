package com.javawebapplication.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serial;
import java.util.Objects;

@Entity
public class Authority implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1272548942962614584L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
    @ManyToOne
    private User user;

    @Transient
    public Boolean isAdmin() {
        if (Objects.equals(authority, "ROLE_ADMIN")) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override @Transient
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority1 = (Authority) o;
        return Objects.equals(getAuthority(), authority1.getAuthority());
    }

    @Override @Transient
    public int hashCode() {
        return Objects.hash(getAuthority());
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}