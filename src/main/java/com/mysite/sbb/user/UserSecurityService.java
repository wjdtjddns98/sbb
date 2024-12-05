package com.mysite.sbb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //시큐리티의 인증을 하기 위해서 메서드를 완성한다
        //이때 유저를 DB 검색하여 유저 객체와 유저 권한을 함께 UserDetails 로 리턴
        Optional<SiteUser> _siteUser = userRepo.findByUsername(username);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 계정입니다");
        }
        SiteUser siteUser = _siteUser.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getRole()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getRole()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);

    }
}
