package br.pds.batepapo.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import br.pds.batepapo.model.Cliente;
import br.pds.batepapo.repository.ClienteRepository;

public class EmissorMensagem {

	public void cadastrarApelido(String nomeCliente, DataOutputStream out_cliente) {
		Cliente cliente = buscarCliente(out_cliente);
		if (!cliente.equals(null)) {
			cliente.setNomeCliente(nomeCliente);
			try {
				out_cliente.writeUTF(nomeCliente + " cadastrado com sucesso");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void listarClientes(DataOutputStream out_cliente) {
		for (Cliente cliente : ClienteRepository.clientes) {
			try {
				out_cliente.writeUTF(cliente.getNomeCliente());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void enviarMensagemTodos(String mensagem, DataOutputStream out_cliente) {
		for (Cliente cliente : ClienteRepository.clientes) {
			if (!cliente.getOut().equals(out_cliente)) {
				try {
					Cliente clienteEmissor = buscarCliente(out_cliente);
					cliente.getOut().writeUTF(
							"[" + clienteEmissor.getNomeCliente() + "][Todos][" + dataMensagem() + "]: " + mensagem);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void enviarMensagemCliente(String mensagem, String nomeCliente, DataOutputStream out_cliente) {
		for (Cliente cliente : ClienteRepository.clientes) {
			if (cliente.getNomeCliente().equals(nomeCliente)) {
				try {
					Cliente clienteEmissor = buscarCliente(out_cliente);
					cliente.getOut().writeUTF("[" + clienteEmissor.getNomeCliente() + "][" + cliente.getNomeCliente()
							+ "][" + dataMensagem() + "]: " + mensagem);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void fecharConexao(DataOutputStream out_cliente) {
		Cliente cliente = buscarCliente(out_cliente);
		if (!cliente.equals(null)) {
			try {
				out_cliente.writeUTF("Conexão Encerrada");
				cliente.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public Cliente buscarCliente(DataOutputStream out) {
		for (Cliente cliente : ClienteRepository.clientes) {
			if (cliente.getOut().equals(out)) {
				return cliente;
			}
		}
		return null;
	}

	public String dataMensagem() {
		Locale locale = new Locale("pt", "BR");
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy' - 'HH':'mm'h'", locale);
		return formatador.format(calendar.getTime());
	}
}
