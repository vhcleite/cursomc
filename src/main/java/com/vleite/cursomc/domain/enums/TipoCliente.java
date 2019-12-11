package com.vleite.cursomc.domain.enums;

import java.util.Arrays;

public enum TipoCliente {
	PESSOA_FISICA(1, "Pessoa Física"), PESSOA_JURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descricao;

	private TipoCliente(int cod, String descricao) {
		this.setCod(cod);
		this.setDescricao(descricao);
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoCliente tipo : TipoCliente.values()) {
			if (tipo.cod == cod)
				return tipo;
		}
		
		throw new IllegalArgumentException(String.format("O codigo passado %s nao corresponde a nenhum tipo de cliente", cod));
	}

}
