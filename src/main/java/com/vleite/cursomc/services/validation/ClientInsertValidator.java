package com.vleite.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vleite.cursomc.domain.enums.TipoCliente;
import com.vleite.cursomc.dto.NewClienteDTO;
import com.vleite.cursomc.resources.exception.FieldError;
import com.vleite.cursomc.utils.BRUtils;

public class ClientInsertValidator implements ConstraintValidator<ClientInsertConstraint, NewClienteDTO> {

	@Override
	public boolean isValid(NewClienteDTO client, ConstraintValidatorContext context) {

		List<FieldError> list = new ArrayList<>();

		if (client.getTipo() == TipoCliente.PESSOA_FISICA && !BRUtils.isValidCPF(client.getCpfOuCnpj())) {
			list.add(new FieldError("cpfOuCnpj", "CPF não válido"));
		}

		if (client.getTipo() == TipoCliente.PESSOA_JURIDICA && !BRUtils.isValidCNPJ(client.getCpfOuCnpj())) {
			list.add(new FieldError("cpfOuCnpj", "CNPJ não válido"));
		}

		for (FieldError e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}

}
