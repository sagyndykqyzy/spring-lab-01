
package kz.iitu.springlab.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final AppUserRepository repository;

    public DatabaseUserDetailsService(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("DATABASE LOGIN ATTEMPT: " + username);

        AppUser appUser = repository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username));
        System.out.println("PASSWORD HASH LENGTH: "
                + appUser.getPasswordHash().length());

        System.out.println("PASSWORD HASH IS BCRYPT: "
                + appUser.getPasswordHash().matches(
                "^\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}$"
        ));

        return User.withUsername(appUser.getUsername())
                .password(appUser.getPasswordHash())
                .disabled(!appUser.isEnabled())
                .roles(appUser.getRoles().toArray(new String[0]))
                .build();
    }
}


