package br.pds.batepapo.proxy;

import java.io.IOException;

import br.pds.batepapo.net.cliente.TCPCliente;

public class Proxy {
	TCPCliente cliente;

	public Proxy(String configuracao) {
		String[] quebraConfiguracao = configuracao.split(" ");
		String serverHost = quebraConfiguracao[0];
		int serverPort = Integer.parseInt(quebraConfiguracao[1]);
		try {
			cliente = TCPCliente.Builder().serverHost(serverHost).serverPort(serverPort).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enviarMensagem(String mensagem) {
		cliente.sendRequest(mensagem);
	}

	public String receberMensagem() {
		return cliente.getResponse();
	}
}
