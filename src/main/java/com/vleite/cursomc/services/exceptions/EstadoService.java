package com.vleite.cursomc.services.exceptions;

import com.vleite.cursomc.domain.Estado;
import com.vleite.cursomc.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository repository;

    public List<Estado> getEstados() {
        return repository.findAllByOrderByNome();
    }
}
