package br.pds.batepapo.net.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPCliente {
	private String serverHost;
	private int serverPort;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setIn(DataInputStream in) {
		this.in = in;
	}

	public void setOut(DataOutputStream out) {
		this.out = out;
	}

	public static TCPClienteBuilder Builder() {
		return new TCPClienteBuilder();
	}

	public void sendRequest(String request) {
		try {
			this.out.writeUTF(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getResponse() {
		try {
			String response = this.in.readUTF();
			if(response.equals("Conexão Encerrada")){
				close();
			}
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private void close(){
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
