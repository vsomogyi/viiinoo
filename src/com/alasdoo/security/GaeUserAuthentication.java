package com.alasdoo.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.alasdoo.entity.WineEnthusiast;

/**
 * Authentication object representing a fully-authenticated user.
 *
 * @author Vilmos Somogyi
 */
public class GaeUserAuthentication implements Authentication {

	private static final long serialVersionUID = 4496359661761374918L;
	
	private final WineEnthusiast principal;
    private final Object details;
    private boolean authenticated;

    public GaeUserAuthentication(WineEnthusiast principal, Object details) {
        this.principal = principal;
        this.details = details;
        authenticated = true;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>(principal.getAuthorities());
    }

    public Object getCredentials() {
        throw new UnsupportedOperationException();
    }

    public Object getDetails() {
        return null;
    }

    public Object getPrincipal() {
        return principal;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        authenticated = isAuthenticated;
    }

    public String getName() {
        return principal.getUser().getUserId();
    }

    @Override
    public String toString() {
        return "GaeUserAuthentication{" +
                "principal=" + principal +
                ", details=" + details +
                ", authenticated=" + authenticated +
                '}';
    }
}
