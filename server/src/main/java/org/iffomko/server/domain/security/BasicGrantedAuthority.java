package org.iffomko.server.domain.security;

import org.iffomko.server.domain.Role;
import org.springframework.security.core.GrantedAuthority;

public class BasicGrantedAuthority implements GrantedAuthority {
    private final Role role;

    private BasicGrantedAuthority(Role role) {
        this.role = role;
    }

    public static BasicGrantedAuthority fromRole(Role role) {
        return new BasicGrantedAuthority(role);
    }

    @Override
    public String getAuthority() {
        return role.toString();
    }
}
