package ar.edu.unnoba.proyecto_river_plate_junin.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/webjars/**", "resources/**", "/css/**","/img/**", "/js/**").permitAll()
                .antMatchers("/**").hasRole("USER")
                .and()
                    .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/").failureUrl("/login?error=true")
                    .usernameParameter("email") //indica a spring que lo que contenga el input con el atributo "email" en la pagina de login va a ser el username 
                    .passwordParameter("password").and().csrf().disable()//indica a spring que lo que contenga el input con el atributo name="password" en la pagina de login va a ser la password 
                .logout()
                    .permitAll()
                    .logoutSuccessUrl("/login?logout");
    }
}