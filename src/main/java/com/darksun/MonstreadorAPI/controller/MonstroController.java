package com.darksun.MonstreadorAPI.controller;

import com.darksun.MonstreadorAPI.entity.Monstro;
import com.darksun.MonstreadorAPI.repository.MonstroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class MonstroController {

    @Autowired
    MonstroRepository monstroRepository;

    @GetMapping("/monstros")
    public List<Monstro> listaMonstros() {
        return monstroRepository.findAll();
    }
    @GetMapping("/monstro/{id}")
    public Optional<Monstro> buscarMonstro(@PathVariable(value = "id") Long id){
        return monstroRepository.findById(id);
    }
}
