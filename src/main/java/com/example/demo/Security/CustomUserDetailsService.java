package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Staff;
import com.example.demo.Repository.StaffRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws InvalidEmailException {
        Staff staff = staffRepository.findByEmail(email);
        if (staff == null || !staff.getEmail().equalsIgnoreCase(email)) {
            throw new InvalidEmailException("Email is not valid");
        }

    

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + staff.getRole().getRoleName().toUpperCase());
        return new User(staff.getEmail(), staff.getPassword(), Collections.singleton(authority));
    }
}
