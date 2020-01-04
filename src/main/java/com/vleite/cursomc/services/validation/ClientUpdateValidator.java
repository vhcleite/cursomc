package com.vleite.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.vleite.cursomc.domain.Cliente;
import com.vleite.cursomc.dto.ClienteDTO;
import com.vleite.cursomc.repositories.ClienteRepository;
import com.vleite.cursomc.resources.exception.FieldError;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdateConstraint, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clientRepository;
	
	@Override
	public boolean isValid(ClienteDTO client, ConstraintValidatorContext context) {

		List<FieldError> list = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer id = Integer.parseInt(map.get("id"));
		
		Cliente savedClient = clientRepository.findByEmail(client.getEmail());
		if (savedClient != null && savedClient.getId() != id) {
			list.add(new FieldError("email", "Email já utilizado"));
		}

		for (FieldError e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}

}
