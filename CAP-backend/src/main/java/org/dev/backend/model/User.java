package org.dev.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.dev.api.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@AllArgsConstructor
@Builder
@Getter
public class User implements UserDetails {

    private int id;

    private String userName;

    private String password;

    private String keyQuestion;

    private String keyAnswer;

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
