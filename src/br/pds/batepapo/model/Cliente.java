package br.pds.batepapo.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import br.pds.batepapo.controller.ServerController;
import br.pds.batepapo.net.server.Server;

public class Cliente {
	private DataInputStream in;
	private DataOutputStream out;
	private String nomeCliente;
	private Socket cliente;

	public Cliente(Socket cliente,DataOutputStream out) {
		try {
			this.in = new DataInputStream(cliente.getInputStream());
			this.out = out;
			this.cliente = cliente;
			new LeitorMensagemServer(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DataOutputStream getOut() {
		return out;
	}
	
	public String getNomeCliente(){
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	public void close(){
		try {
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class LeitorMensagemServer extends Thread {
	private DataInputStream in;
	private String mensagem;
	private DataOutputStream out;
	private ServerController serverControl;

	public LeitorMensagemServer(DataInputStream in, DataOutputStream out) {
		this.in = in;
		this.out = out;
		serverControl = new ServerController();
		this.start();
	}

	public void run() {
		try {
			while (true) {
				mensagem = in.readUTF();
				serverControl.despachante(mensagem, out);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}