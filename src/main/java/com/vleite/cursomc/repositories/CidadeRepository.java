package com.vleite.cursomc.repositories;

import com.vleite.cursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vleite.cursomc.domain.Cidade;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

    @Query("select obj from Cidade obj where obj.estado.id = :estadoId order by obj.nome ")
    public List<Cidade> findByEstado(@Param("estadoId") Integer estadoId);

}
