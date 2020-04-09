package com.zanella.cursomc.resources;

import com.zanella.cursomc.domain.Cliente;
import com.zanella.cursomc.dto.ClienteDTO;
import com.zanella.cursomc.dto.ClienteNewDTO;
import com.zanella.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente obj = service.find(id);
		return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
        Cliente obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
        Cliente obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDto = list
                .stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> listDto = list
                .map(obj -> new ClienteDTO(obj));
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }
}
