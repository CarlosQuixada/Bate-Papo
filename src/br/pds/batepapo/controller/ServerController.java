package br.pds.batepapo.controller;

import java.io.DataOutputStream;

import br.pds.batepapo.service.Decodificador;

public class ServerController {
	private Decodificador decodificador;
	
	public ServerController() {
		decodificador = new Decodificador();
	}
	
	public void despachante(String mensagem, DataOutputStream out_cliente){
		decodificador.decodificar(mensagem,out_cliente);
	}
}
