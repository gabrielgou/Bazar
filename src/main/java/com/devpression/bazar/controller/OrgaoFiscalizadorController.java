package com.devpression.bazar.controller;

import com.devpression.bazar.model.OrgaoDonatario;
import com.devpression.bazar.model.OrgaoFiscalizador;
import com.devpression.bazar.repositorio.RepositorioOD;
import com.devpression.bazar.repositorio.RepositorioOF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
public class OrgaoFiscalizadorController {
    @CrossOrigin("*")
    @PostMapping("/orgaoFiscalizador")
    public ResponseEntity<?> create(@RequestBody OrgaoFiscalizador of)
    {
        try {
            RepositorioOF.getCurrentInstance().create(of);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @CrossOrigin("*")
    @GetMapping("/orgaoFiscalizador/{id}")
    public ResponseEntity<OrgaoFiscalizador> read(@PathVariable("id") int id)
    {
        try {
            OrgaoFiscalizador of = RepositorioOF.getCurrentInstance().read(id);
            if(of!=null)
                return new ResponseEntity<>(of,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @DeleteMapping("/orgaoFiscalizador/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id)
    {
        try {
            RepositorioOF.getCurrentInstance().delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @PutMapping("/orgaoFiscalizador")
    public ResponseEntity<?> update(@RequestBody OrgaoFiscalizador of)
    {
        try {
            RepositorioOF.getCurrentInstance().update(of);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @GetMapping("/orgaoFiscalizador")
    public ResponseEntity<List<OrgaoFiscalizador>> readAll()
    {
        try {
            List<OrgaoFiscalizador> p = RepositorioOF.getCurrentInstance().readAll();
            if(p!=null)
                return new ResponseEntity<>(p,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
