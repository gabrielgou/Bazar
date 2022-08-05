package com.devpression.bazar.controller;

import com.devpression.bazar.model.Produto;
import com.devpression.bazar.repositorio.RepositorioFactory;
import com.devpression.bazar.repositorio.RepositorioProduto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@RestController
public class ProdutoController {
    @CrossOrigin("*")
    @PostMapping("/produto")
    public ResponseEntity<?> create(@RequestBody Produto produto)
    {
        try {
            RepositorioFactory.Produto.getInstance().create(produto);
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
            Produto p = (Produto) RepositorioFactory.Produto.getInstance().read(codigo);
            if(p!=null)
                return new ResponseEntity<>(p,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @DeleteMapping("/produto/{codigo}")
    public ResponseEntity<?> delete(@PathVariable("codigo") int codigo)
    {
        try {
            RepositorioFactory.Produto.getInstance().delete(codigo);
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
            RepositorioFactory.Produto.getInstance().update(p);
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
            List<Produto> p = RepositorioFactory.Produto.getInstance().readAll();
            if(p!=null)
                return new ResponseEntity<>(p,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
