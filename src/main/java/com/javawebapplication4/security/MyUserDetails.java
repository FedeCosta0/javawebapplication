package com.javawebapplication4.security;

import com.javawebapplication4.domain.User;

import org.springframework.security.core.userdetails.UserDetails;


public class MyUserDetails extends User implements UserDetails {

    public MyUserDetails(){}

    public MyUserDetails(User user){
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setFirstname(user.getFirstname());
        this.setLastname(user.getLastname());
        this.setAuthorities(user.getAuthorities());
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
