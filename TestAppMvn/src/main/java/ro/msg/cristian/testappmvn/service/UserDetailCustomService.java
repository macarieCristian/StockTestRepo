package ro.msg.cristian.testappmvn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.msg.cristian.testappmvn.domain.AppUser;
import ro.msg.cristian.testappmvn.repository.AppUserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailCustomService implements UserDetailsService {

    private AppUserRepository appUserRepository;

    @Autowired
    public UserDetailCustomService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.getAppUserByUsername(username);
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
