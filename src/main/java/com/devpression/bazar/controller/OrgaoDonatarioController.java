package com.devpression.bazar.controller;

import com.devpression.bazar.model.OrgaoDonatario;
import com.devpression.bazar.repositorio.RepositorioOD;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
public class OrgaoDonatarioController {
    @CrossOrigin("*")
    @PostMapping("/orgaoDonatario")
    public ResponseEntity<?> create(@RequestBody OrgaoDonatario od)
    {
        try {
            RepositorioOD.getCurrentInstance().create(od);
            return new ResponseEntity<>("Orgão Donatario Adicionado",HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
    @CrossOrigin("*")
    @GetMapping("/orgaoDonatario/{id}")
    public ResponseEntity<OrgaoDonatario> read(@PathVariable("id") int id)
    {
        try {
            OrgaoDonatario od = RepositorioOD.getCurrentInstance().read(id);
            if(od!=null)
                return new ResponseEntity<>(od,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @DeleteMapping("/orgaoDonatario/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id)
    {
        try {
            RepositorioOD.getCurrentInstance().delete(id);
            return new ResponseEntity<>("Orgão Donatario Excluido",HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin("*")
    @PutMapping("/orgaoDonatario")
    public ResponseEntity<?> update(@RequestBody OrgaoDonatario p)
    {
        try {
            RepositorioOD.getCurrentInstance().update(p);
            return new ResponseEntity<>("Orgão Donatário Alterado",HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin("*")
    @GetMapping("/orgaoDonatario")
    public ResponseEntity<List<OrgaoDonatario>> readAll()
    {
        try {
            List<OrgaoDonatario> p = RepositorioOD.getCurrentInstance().readAll();
            if(p!=null)
                return new ResponseEntity<>(p,HttpStatus.OK);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
