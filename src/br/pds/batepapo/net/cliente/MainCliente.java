package br.pds.batepapo.net.cliente;

import java.util.Scanner;

import br.pds.batepapo.proxy.Proxy;

public class MainCliente {
	private Proxy proxy;

	public MainCliente(String configuracao) {
		proxy = new Proxy(configuracao);
	}

	public static void main(String[] args) {
		Scanner ler = new Scanner(System.in);
		String mensagem;
		System.out.print("./cliente ");
		String configuracao = ler.nextLine();

		MainCliente cliente = new MainCliente(configuracao);
		new LeitorMensagem(cliente.proxy);
		while (true) {
			mensagem = ler.nextLine();
			cliente.proxy.enviarMensagem(mensagem);
		}
	}
}

class LeitorMensagem extends Thread {
	private Proxy proxy;
	private String mensagem;

	public LeitorMensagem(Proxy proxy) {
		this.proxy = proxy;
		this.start();
	}

	public void run() {
		while (true) {
			mensagem = proxy.receberMensagem();
			if(mensagem.equals("Conexão Encerrada"))
			this.destroy();
			System.out.println(mensagem);
		}
	}
}