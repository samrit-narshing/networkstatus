package com.project.config.core.dao.impl;

import com.project.config.core.dao.UsersUserDetails;
import com.project.dao.user.UserService;
import com.project.model.user.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Samrit
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserResource user;
        try {
            user = userService.readUserByUsername_ONLY_FOR_REST_LOGIN(username);
            System.out.println("USER " + username);
            if (user == null) {
                throw new UsernameNotFoundException("no user found with " + username);
            } else {
                System.out.println("User Name : " + user.getUsername() + " Password :" + user.getPassword());
            }
        } catch (Exception e) {
            e.printStackTrace();
            user = new UserResource();
        }
        return new UsersUserDetails(user);

        //end of test
    }
}
