package com.maculuve.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User  implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", unique = true, length = 180)
    private String userName;
    @Column(name = "full_name", unique = true, length = 180)
    private String fullName;
    @Column(nullable = false)
    private String password;
    @Column(name = "account_non_expired", nullable = false)
    private Boolean accountNonExpired;
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;
    @Column(name = "credentials_non_expired", nullable = false)
    private Boolean credentialsNonExpired;
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission", joinColumns = { @JoinColumn(name = "id_user") }, inverseJoinColumns = {
            @JoinColumn(name = "id_permission") })
    private List<Permission> permissions;

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();

        for (Permission permission : permissions) {
            roles.add(permission.getDescription());

        }
        return roles;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((accountNonExpired == null) ? 0 : accountNonExpired.hashCode());
        result = prime * result + ((accountNonLocked == null) ? 0 : accountNonLocked.hashCode());
        result = prime * result + ((credentialsNonExpired == null) ? 0 : credentialsNonExpired.hashCode());
        result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
        result = prime * result + ((permissions == null) ? 0 : permissions.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        if (fullName == null) {
            if (other.fullName != null)
                return false;
        } else if (!fullName.equals(other.fullName))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (accountNonExpired == null) {
            if (other.accountNonExpired != null)
                return false;
        } else if (!accountNonExpired.equals(other.accountNonExpired))
            return false;
        if (accountNonLocked == null) {
            if (other.accountNonLocked != null)
                return false;
        } else if (!accountNonLocked.equals(other.accountNonLocked))
            return false;
        if (credentialsNonExpired == null) {
            if (other.credentialsNonExpired != null)
                return false;
        } else if (!credentialsNonExpired.equals(other.credentialsNonExpired))
            return false;
        if (enabled == null) {
            if (other.enabled != null)
                return false;
        } else if (!enabled.equals(other.enabled))
            return false;
        if (permissions == null) {
            if (other.permissions != null)
                return false;
        } else if (!permissions.equals(other.permissions))
            return false;
        return true;
    }

}
