package br.pds.batepapo.net.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClienteBuilder {
	private TCPCliente cliente;
	
	public TCPClienteBuilder(){
		this.cliente = new TCPCliente();
	}
	
	public TCPClienteBuilder serverHost(String serverHost){
		this.cliente.setServerHost(serverHost);
		return this;
	}
	
	public TCPClienteBuilder serverPort(int serverPort){
		this.cliente.setServerPort(serverPort);
		return this;
	}
	
	public TCPCliente build() throws UnknownHostException, IOException{
		Socket socket = new Socket(
				this.cliente.getServerHost(),
				this.cliente.getServerPort());
		
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
		cliente.setIn(in);
		cliente.setOut(out);
		cliente.setSocket(socket);
		
		return this.cliente;
	}
}
