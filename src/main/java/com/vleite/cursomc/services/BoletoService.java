package com.vleite.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.vleite.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void fillDataVencimento(PagamentoComBoleto pagamento, Date instantePedido) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(instantePedido);
		calendario.add(Calendar.DAY_OF_MONTH, 7);
		pagamento.setDataVencimento(calendario.getTime());
	}

}
