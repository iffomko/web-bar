package org.iffomko.server.domain.security;

import org.iffomko.server.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Stream;

public class BasicUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Collection<BasicGrantedAuthority> basicGrantedAuthorities;

    private BasicUserDetails(String username, String password, Collection<BasicGrantedAuthority> basicGrantedAuthorities) {
        this.username = username;
        this.password = password;
        this.basicGrantedAuthorities = basicGrantedAuthorities;
    }

    public static BasicUserDetails fromUser(User user) {
        var authorities = Stream.of(user.getRole()).map(BasicGrantedAuthority::fromRole).toList();
        return new BasicUserDetails(user.getPhone(), user.getPassword(), authorities);
    }

    @Override
    public Collection<BasicGrantedAuthority> getAuthorities() {
        return basicGrantedAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
