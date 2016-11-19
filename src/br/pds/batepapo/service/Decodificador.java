package br.pds.batepapo.service;

import java.io.DataOutputStream;
import java.io.IOException;

public class Decodificador {
	private EmissorMensagem emissor = new EmissorMensagem();

	public void decodificar(String mensagem, DataOutputStream out_cliente) {
		String[] identificadorMensagem = mensagem.split(" ");

		switch (identificadorMensagem[0]) {
		case "/apelido":
			mensagem = mensagem.substring(9);
			emissor.cadastrarApelido(mensagem, out_cliente);
			break;
		
		case "/quem":
			emissor.listarClientes(out_cliente);
			break;
		
		case "/todos":
			mensagem = mensagem.substring(6);
			emissor.enviarMensagemTodos(mensagem, out_cliente);
			break;
		
		case "/para":
			mensagem = mensagem.substring(6 + identificadorMensagem[1].length());
			String nomeReceptor = identificadorMensagem[1];
			emissor.enviarMensagemCliente(mensagem, nomeReceptor,out_cliente);
			break;
		
		case "/tchau":
			emissor.fecharConexao(out_cliente);
			break;
		default:
			try {
				out_cliente.writeUTF("Comando Incorreto!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
}
