package com.zanella.cursomc.services;

import com.zanella.cursomc.domain.Cliente;
import com.zanella.cursomc.repositories.ClienteRepository;
import com.zanella.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente find(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! " + "id: " + id));
    }
}
