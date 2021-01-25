package com.vleite.cursomc.resources;

import com.vleite.cursomc.dto.EstadoDto;
import com.vleite.cursomc.services.exceptions.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value = "/estados")
public class EstadoResource {
    @Autowired
    private EstadoService service;

    @GetMapping()
    public ResponseEntity<List<EstadoDto>> getEstados() {
        List<EstadoDto> estados = service.getEstados()
                .stream()
                .map(e -> new EstadoDto(e.getId(), e.getNome()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(estados);
    }
}
