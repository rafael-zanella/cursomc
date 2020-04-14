package com.zanella.cursomc.services;

import com.zanella.cursomc.domain.Cliente;
import com.zanella.cursomc.repositories.ClienteRepository;
import com.zanella.cursomc.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cli = clienteRepository.findByEmail(email);
        if(cli == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserDetailsImpl(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfil());
    }
}
