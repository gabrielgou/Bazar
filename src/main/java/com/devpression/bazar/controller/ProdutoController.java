package com.devpression.bazar.controller;

import com.devpression.bazar.model.Produto;
import com.devpression.bazar.repositorio.RepositorioProduto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ProdutoController {
    @CrossOrigin("*")
    @PostMapping("/produto")
    public ResponseEntity<?> create(@RequestBody Produto produto)
    {
        try {
            RepositorioProduto.getCurrentInstance().create(produto);
            return new ResponseEntity<>("Cadastrado com Sucesso",HttpStatus.CREATED);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
    @CrossOrigin("*")
    @GetMapping("/produto/{codigo}")
    public ResponseEntity<Produto> read(@PathVariable("codigo") int codigo)
    {
        try {
            Produto p = RepositorioProduto.getCurrentInstance().read(codigo);
            if(p!=null)
                return new ResponseEntity<>(p,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @DeleteMapping("/produto/{codigo}")
    public ResponseEntity<?> delete(@PathVariable("codigo") int codigo)
    {
        try {
            RepositorioProduto.getCurrentInstance().delete(codigo);
            return new ResponseEntity<>("Produto Apagado", HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @PutMapping("/produto")
    public ResponseEntity<?> update(@RequestBody Produto p)
    {
        try {
            RepositorioProduto.getCurrentInstance().update(p);
            return new ResponseEntity<>("Produto Atualizado", HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin("*")
    @GetMapping("/produto")
    public ResponseEntity<List<Produto>> readAll()
    {
        try {
            List<Produto> p = RepositorioProduto.getCurrentInstance().readAll();
            if(p!=null)
                return new ResponseEntity<>(p,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
