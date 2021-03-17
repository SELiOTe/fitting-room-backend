package com.seliote.fr.config.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

/**
 * Spring Security UserDetails 实现
 *
 * @author seliote
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final String username;
    private final boolean enable = true;

    public UserDetails(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enable;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enable;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
