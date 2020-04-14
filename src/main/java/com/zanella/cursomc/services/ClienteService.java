package com.zanella.cursomc.services;

import com.zanella.cursomc.domain.Cidade;
import com.zanella.cursomc.domain.Cliente;
import com.zanella.cursomc.domain.Endereco;
import com.zanella.cursomc.domain.enums.Perfil;
import com.zanella.cursomc.domain.enums.TipoCliente;
import com.zanella.cursomc.dto.ClienteDTO;
import com.zanella.cursomc.dto.ClienteNewDTO;
import com.zanella.cursomc.repositories.CidadeRepository;
import com.zanella.cursomc.repositories.ClienteRepository;
import com.zanella.cursomc.repositories.EnderecoRepository;
import com.zanella.cursomc.security.UserDetailsImpl;
import com.zanella.cursomc.services.exceptions.AuthorizationException;
import com.zanella.cursomc.services.exceptions.DataIntegrityException;
import com.zanella.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Cliente find(Integer id) {

        UserDetailsImpl user = UserService.authenticated();
        if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado!");
        }

        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! " + "id: " + id));
    }

    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    public void delete(Integer id){
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir um Cliente que possui entidades relacionadas.");
        }
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(),
                objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()),
                bCryptPasswordEncoder.encode(objDto.getSenha()));
        Optional<Cidade> cid = cidadeRepository.findById(objDto.getCidadeId());
        Endereco end = new Endereco(
                null, objDto.getLogradouro(), objDto.getNumero(),
                objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli,
                cid.orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada."))
        );

        cli.getEnderecos().add(end);
        cli.getTelefones().addAll(Arrays.asList(objDto.getTelefone1(), objDto.getTelefone2(), objDto.getTelefone3()));

        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
