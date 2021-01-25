package com.vleite.cursomc.dto;

import java.util.Objects;

public class CidadeDto {

    private Integer id;
    private String nome;

    public CidadeDto() {

    }

    public CidadeDto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CidadeDto cidadeDto = (CidadeDto) o;
        return Objects.equals(id, cidadeDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CidadeDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
