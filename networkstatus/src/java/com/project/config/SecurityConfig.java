package com.project.config;

import com.project.config.core.dao.impl.UserDetailServiceImpl;
import com.project.util.CryptUtil;
import com.project.util.Global;
import com.project.util.SHA1;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserDetailServiceImpl userDetailService;

//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.jdbcAuthentication().dataSource(dataSource)
//                //                .usersByUsernameQuery("select username,password, enabled from users where username=?")
//                //                .authoritiesByUsernameQuery("select u.username as username, r.role as role FROM user_roles ur inner join users u on ur.user_id=u.id inner join roles r on ur.role_id = r.id where u.username=?")
//                .usersByUsernameQuery("SELECT U.user_name AS 'username', U.user_pass AS 'password', U.enabled AS 'enabled' FROM users U where U.user_name=?")
//                .authoritiesByUsernameQuery("SELECT U.user_name AS 'username', A.role_name AS 'authority' FROM users U JOIN user_roles A ON U.user_name = A.user_name WHERE U.user_name=?")
//                .passwordEncoder(new ShaPasswordEncoder());
//    }
//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .eraseCredentials(false)
//                .jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username,password, enabled from user where username=?")
//                .authoritiesByUsernameQuery("select u.username as username, r.name as role FROM user_role ur inner join user u on ur.user_id=u.id inner join role r on ur.role_id = r.id where u.username=? and (neverexpire=true OR STR_TO_DATE(accountexpiration, '%Y-%m-%d') >= DATE_FORMAT(NOW() , '%Y-%m-%d 00:00:00'))")
//                .passwordEncoder(
//                        //                        new ShaPasswordEncoder()
//                        new PasswordEncoder() {
//
//                    public String encode(CharSequence rawPassword) {
//                        return null; // TODO implement
//                    }
//
//                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                        try {
//                            //sha password encryption
//                            CryptUtil cryptUtil = new CryptUtil();
//                            String encodedRawPassword = (cryptUtil.asHex(SHA1.SHA1(rawPassword.toString())));
//                            return encodedRawPassword.equals(encodedPassword); // TODO implement
//                        } catch (Exception e) {
//                            return false;
//                        }
//                    }
//                }
//                );
//    }
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .eraseCredentials(false)
                .userDetailsService(userDetailService)
                .passwordEncoder(new PasswordEncoder() {

                    public String encode(CharSequence rawPassword) {
                        return null; // TODO implement
                    }

                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        try {
                            //sha password encryption
//                            javax.swing.JOptionPane.showMessageDialog(null, "Raw--"+rawPassword+"--encoded---"+encodedPassword);
                            CryptUtil cryptUtil = new CryptUtil();
                            String encodedRawPassword = (cryptUtil.asHex(SHA1.SHA1(rawPassword.toString())));
                            return encodedRawPassword.equals(encodedPassword); // TODO implement
                        } catch (Exception e) {
                            return false;
                        }
                    }
                });
    }

    // Another Securtity Configuration to ByBass CSRF Security i.e for those pages that have forms which don't have csrf id in hidden field
    @Order(1)
    @Configuration
    static class resoucesAccessConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .headers().disable()
                    .antMatcher("/resources/**")
                    .authorizeRequests().anyRequest().permitAll();
        }
    }

    @Order(2)
    @Configuration
    static class AnotherAdminConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .headers().disable()
                    .antMatcher("/othersources/**")
                    .authorizeRequests().anyRequest().hasRole("ADMIN");
        }
    }

//        @Order(3)
//    @Configuration
//    static class errorPageAccessConfig extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .headers().disable()
//                    .antMatcher("/403")
//                    .authorizeRequests().anyRequest().permitAll();
//        }
//    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/rest/device/checkuser");
        web.ignoring().antMatchers("/rest/device/**");
        web.ignoring().antMatchers("/rest/web/checkuser");
        web.ignoring().antMatchers("/rest/web/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/rest/device/**").permitAll()
                .antMatchers("/rest/web/**").permitAll()
                .antMatchers("/404", "/500", "/500_uploadSizeError", "/index.jsp", "/home", "/userman/user/changePassword", "/userman/user/changePassword/submit", "/result", "/sample.jsp", "/redirect_change_password_config.jsp", "/userman/user/password_expired/changePassword", "/userman/user/password_expired/changePassword/submit", "/userman/user/send_email/submit", "/newsman/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DEPARTMENT_USER') or hasRole('ROLE_STUDENT') or hasRole('ROLE_PARENT') or hasRole('ROLE_PROFESSOR') or hasRole('ROLE_MOTORIST') or hasRole('ROLE_FRIEND') or hasRole('ROLE_TRAVEL_ADMINISTRATOR') or hasRole('ROLE_TRAVELER') or hasRole('ROLE_TRAVEL_GUIDE') or hasRole('ROLE_SCHOOL_ADMINISTRATOR')")
                .antMatchers("/networkman/dynamic_chart/networkgroup/**","/networkman/networkgroup/node/double_click/**").access("hasRole('ROLE_DEPARTMENT_USER') or hasRole('ROLE_USER')")
                .antMatchers("/networkman/**","/departmentuserman/**").access("hasRole('ROLE_USER')")
                //                .antMatchers("/studentman/student/professor/**", "/classgroupman/classgroup/forprofessor/**").access("hasRole('ROLE_PROFESSOR')")
                //                .antMatchers("/studentman/student/motorist/**").access("hasRole('ROLE_MOTORIST')")
                //                .antMatchers("/parentman/parent/student/**", "/classgroupman/classgroup/forstudent/**", "/classgroupman/classgroup/view").access("hasRole('ROLE_STUDENT')")
                //                .antMatchers("/dashboardman/school/**", "/chartman/school/**", "/reportman/school/**").access("hasRole('ROLE_USER') or hasRole('ROLE_PROFESSOR') or hasRole('ROLE_MOTORIST') or hasRole('ROLE_SCHOOL_ADMINISTRATOR') or hasRole('ROLE_SCHOOL_USER')")
                //                .antMatchers("/nfcscannedcardinfoman/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_PARENT') or hasRole('ROLE_PROFESSOR') or hasRole('ROLE_MOTORIST') or hasRole('ROLE_SCHOOL_ADMINISTRATOR') or hasRole('ROLE_SCHOOL_USER')")
                //                .antMatchers("/friendrelationshipman/friend/**").access("hasRole('ROLE_FRIEND')")
                //                .antMatchers("/loationman/**", "/pushmessageserverman/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SCHOOL_ADMINISTRATOR') or hasRole('ROLE_STUDENT') or hasRole('ROLE_PARENT') or hasRole('ROLE_PROFESSOR') or hasRole('ROLE_MOTORIST') or hasRole('ROLE_FRIEND') or hasRole('ROLE_TRAVEL_ADMINISTRATOR') or hasRole('ROLE_TRAVELER') or hasRole('ROLE_TRAVEL_GUIDE') or hasRole('ROLE_SCHOOL_USER')")
                //                .antMatchers("/travelerman/traveler/**", "/travelguideman/travelguide/**", "/travelgroupman/travelgroup/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_TRAVEL_ADMINISTRATOR') or hasRole('ROLE_TRAVELER') or hasRole('ROLE_TRAVEL_GUIDE')")
                .antMatchers("/system/control_panel/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DEPARTMENT_USER')")
                //                .antMatchers("/parentman/**", "/studentman/**", "/professorman/**", "/motoristman/**", "/schooloperatorman/**", "/deviceman/**", "/grademan/**", "/courseman/**", "/classgroupman/**", "/attendanceman/**").access("hasRole('ROLE_SCHOOL_ADMINISTRATOR') or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SCHOOL_USER')")
                //                .antMatchers("/company_info_man/company_info/admin/**").access("hasRole('ROLE_TRAVEL_ADMINISTRATOR') or hasRole('ROLE_SCHOOL_ADMINISTRATOR')")
                .antMatchers("/**").access("hasRole('ROLE_ADMINISTRATOR')")
                //                .antMatchers("/admin/**", "/userman/**").access("hasRole('ROLE_ADMINISTRATOR')")

                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf();

    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("joke").password("joke").roles("USER");
//    }
}
