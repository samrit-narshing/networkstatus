package com.project.config.core.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.project.model.user.RoleResource;
import com.project.model.user.UserResource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author Samrit
 */
public class UsersUserDetails implements UserDetails {

    private final UserResource userResource;

    public UsersUserDetails(UserResource userResource) {
        this.userResource = userResource;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        for (RoleResource role : userResource.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        
        return grantedAuthorities;
        
//        GrantedAuthority authority = new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "USER";
//            }
//        };
//
//        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(authority);
//        return authorities;
    }

    @Override
    public String getPassword() {
        return userResource.getPassword();
    }

    @Override
    public String getUsername() {
        return userResource.getUsername();
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
