package com.felipe.garrido.clientCRUD.Security.services;

import com.felipe.garrido.clientCRUD.models.Users;
import com.felipe.garrido.clientCRUD.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersDetailsServiceImplements implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = usersRepository.findByRut(s)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + s));

        return UsersDetailsImplements.build(user);
    }

}
