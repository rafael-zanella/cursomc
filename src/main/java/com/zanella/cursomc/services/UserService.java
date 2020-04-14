package com.zanella.cursomc.services;

import com.zanella.cursomc.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    // retorna o usuario logado no sistema
    public static UserDetailsImpl authenticated() {
        try {
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
