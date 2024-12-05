package com.mysite.sbb.user;

import com.mysite.sbb.question.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);
        siteUser.setPassword(passwordEncoder.encode(password));

        userRepo.save(siteUser);
        return siteUser;

    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = userRepo.findByUsername(username);
        if(siteUser.isPresent()) {
            return siteUser.get();
        }else{
            throw new DataNotFoundException("User not found");
        }
    }
}
