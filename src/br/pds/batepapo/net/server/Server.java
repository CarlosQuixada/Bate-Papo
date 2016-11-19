package br.pds.batepapo.net.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import br.pds.batepapo.model.Cliente;
import br.pds.batepapo.repository.ClienteRepository;

public class Server {
	private Socket cliente;
	private ServerSocket listenSocket;
	private static List<Cliente> clientes = ClienteRepository.clientes;

	public Server(int serverPort) {
		try {
			listenSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try {
			Scanner ler = new Scanner(System.in);
			System.out.print("./servidor ");
			int serverPort = ler.nextInt();
			Server server = new Server(serverPort);
			System.out.println("Servidor Iniciado com Sucesso");
			while (true) {
				DataOutputStream out;
				server.cliente = server.listenSocket.accept();
				
				out = new DataOutputStream(server.cliente.getOutputStream());
				
				Cliente cliente = new Cliente(server.cliente,out);
				clientes.add(cliente);
				out.writeUTF("Conexão Estabelecida com Sucesso");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

