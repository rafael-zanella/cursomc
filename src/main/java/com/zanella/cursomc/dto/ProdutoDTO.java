package com.zanella.cursomc.dto;

import com.zanella.cursomc.domain.Produto;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private Integer id;
    private String nome;
    private Double preco;

    public ProdutoDTO() {}

    public Integer getId() {
        return id;
    }

    public ProdutoDTO(Produto obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.preco = obj.getPreco();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
