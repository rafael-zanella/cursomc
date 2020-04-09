package com.zanella.cursomc.services;

import com.zanella.cursomc.domain.Categoria;
import com.zanella.cursomc.domain.Produto;
import com.zanella.cursomc.repositories.CategoriaRepository;
import com.zanella.cursomc.repositories.ProdutoRepository;
import com.zanella.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        Optional<Produto> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! " + "id: " + id));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return repository.search(nome, categorias, pageRequest);
    }
}
