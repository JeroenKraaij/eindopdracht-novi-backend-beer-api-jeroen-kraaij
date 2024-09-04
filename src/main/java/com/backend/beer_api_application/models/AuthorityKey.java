package com.backend.beer_api_application.models;

import java.io.Serializable;
import java.util.Objects;

public class AuthorityKey implements Serializable {

    private String username;
    private String authority;

    public AuthorityKey() {}

    public AuthorityKey(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    // Override equals and hashCode for composite key comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityKey that = (AuthorityKey) o;
        return Objects.equals(username, that.username) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }
}
