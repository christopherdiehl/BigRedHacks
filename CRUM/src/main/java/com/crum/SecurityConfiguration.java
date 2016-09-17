package com.crum;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

      httpSecurity
      .authorizeRequests()
        .anyRequest().authenticated()
        .and()
      .formLogin()
        .loginPage("/login")
        .failureUrl("/login-error")
        .permitAll()
        .defaultSuccessUrl("/index");
  } 
  
  @Autowired
  public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
      builder.userDetailsService(userDetailsService); 
  }

}

@EnableWebSecurity
@Configuration
@Component
public class SecUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }else{
            UserDetails details = new SecUserDetails(user);
            return details;
        }
    }
}


