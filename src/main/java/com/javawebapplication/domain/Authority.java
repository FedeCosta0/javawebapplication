package com.javawebapplication.domain;

import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serial;
import java.util.Objects;

@Entity
public class Authority implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1272548942962614584L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String authority;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Boolean isAdmin() {
        if (Objects.equals(authority, "ROLE_ADMIN"))
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    public Boolean isUser() {
        if (Objects.equals(authority, "ROLE_USER"))
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Authority authority = (Authority) o;
        return id != null && Objects.equals(id, authority.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}