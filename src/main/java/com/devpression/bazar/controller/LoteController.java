package com.devpression.bazar.controller;

import com.devpression.bazar.model.Lote;
import com.devpression.bazar.repositorio.RepositorioFactory;
import com.devpression.bazar.repositorio.RepositorioLote;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
public class LoteController {
    @CrossOrigin("*")
    @PostMapping("/lote")
    public ResponseEntity<?> create(@RequestBody Lote l)
    {
        try {
            l.setDataEntrega(new Date());
            RepositorioFactory.Lote.getInstance().create(l);
            return new ResponseEntity<>("Lote Adicionado",HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
    @CrossOrigin("*")
    @GetMapping("/lote/{id}")
    public ResponseEntity<Lote> read(@PathVariable("id") int id)
    {
        try {
            Lote of = (Lote) RepositorioFactory.Lote.getInstance().read(id);
            if(of!=null)
                return new ResponseEntity<>(of,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @DeleteMapping("/lote/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id)
    {
        try {
            RepositorioFactory.Lote.getInstance().delete(id);
            return new ResponseEntity<>("Lote Deletado",HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @PutMapping("/lote")
    public ResponseEntity<?> update(@RequestBody Lote of)
    {
        try {
            RepositorioFactory.Lote.getInstance().update(of);
            return new ResponseEntity<>("Lote Alterado",HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin("*")
    @GetMapping("/lote/produto/{nome}")
    public ResponseEntity<?> filter(@PathVariable("nome") String nome)
    {
        try {
            List<Lote> p = RepositorioLote.getCurrentInstance().filter(nome);
            if(p!=null)
                return new ResponseEntity<>(p,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    @CrossOrigin("*")
    @GetMapping("/lote")
    public ResponseEntity<List<Lote>> readAll()
    {
        try {
            List<Lote> p = RepositorioFactory.Lote.getInstance().readAll();
            if(p!=null)
                return new ResponseEntity<>(p,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


}
