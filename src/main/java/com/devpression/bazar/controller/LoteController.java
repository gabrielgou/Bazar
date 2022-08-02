package com.devpression.bazar.controller;

import com.devpression.bazar.model.Lote;
import com.devpression.bazar.repositorio.RepositorioLote;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@RestController
public class LoteController {
    @CrossOrigin("*")
    @PostMapping("/lote")
    public ResponseEntity<?> create(@RequestBody Lote l)
    {
        try {
            RepositorioLote.getCurrentInstance().create(l);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @CrossOrigin("*")
    @GetMapping("/lote/{id}")
    public ResponseEntity<Lote> read(@PathVariable("id") int id)
    {
        try {
            Lote of = RepositorioLote.getCurrentInstance().read(id);
            if(of!=null)
                return new ResponseEntity<>(of,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/lote/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id)
    {
        try {
            RepositorioLote.getCurrentInstance().delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/lote")
    public ResponseEntity<?> update(@RequestBody Lote of)
    {
        try {
            RepositorioLote.getCurrentInstance().update(of);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin("*")
    @GetMapping("/lote")
    public ResponseEntity<List<Lote>> readAll()
    {
        try {
            List<Lote> p = RepositorioLote.getCurrentInstance().readAll();
            if(p!=null)
                return new ResponseEntity<>(p,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
