package com.vleite.cursomc.services;

import com.vleite.cursomc.domain.Cidade;
import com.vleite.cursomc.domain.Estado;
import com.vleite.cursomc.repositories.CidadeRepository;
import com.vleite.cursomc.services.exceptions.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    public CidadeRepository repository;

    @Autowired
    public EstadoService estadoService;

    public List<Cidade> findCidadesByEstado(Integer estadoId) {
        return repository.findByEstado(estadoId);
    }

}
