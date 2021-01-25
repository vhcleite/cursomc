package com.vleite.cursomc.resources;

import com.sun.mail.iap.Response;
import com.vleite.cursomc.dto.CidadeDto;
import com.vleite.cursomc.dto.EstadoDto;
import com.vleite.cursomc.services.CidadeService;
import com.vleite.cursomc.services.exceptions.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value = "/estados")
public class EstadoResource {
    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping()
    public ResponseEntity<List<EstadoDto>> getEstados() {
        List<EstadoDto> estados = service.getEstados()
                .stream()
                .map(e -> new EstadoDto(e.getId(), e.getNome()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(estados);
    }

    @GetMapping(value = "{estadoId}/cidades")
    public ResponseEntity<List<CidadeDto>> getCidades(@PathVariable Integer estadoId) {
        List<CidadeDto> cidades = cidadeService.findCidadesByEstado(estadoId).stream()
                .map(c -> new CidadeDto(c.getId(), c.getNome()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cidades);
    }
}
