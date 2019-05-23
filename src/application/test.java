package application;

import java.util.ArrayList;

public class test {

	public static void main(String[] args) {
		BancoDeDados bd = new BancoDeDados();
		bd.conectar();
		if (bd.estaConectado()) {
//			for(int i = 0; i < bd.listarClientes("nomeCli", "aa").size(); i++) {   
//			    System.out.print(bd.listarClientes("nomeCli", "aa").get(i) + "\n\n");
//			} 
//			ArrayList<String> lista = bd.listarNomesCli("nomeCli", "aa");
//			ArrayList<String> lista2 = bd.listarNomesDev("nomeDev", "aa");
//			int tam = lista.size();
//			int tam2 = lista2.size();
//			for (int i = 0; i < tam; i++) {
//				for (int j = 0; j < tam2; j++) {
//					System.out.println(lista.get(i) + "\t" + lista2.get(j) + "\n\n");
//				}
//			}
			ArrayList<String> lista = bd.listarDividaCruzada("identidadeCli","identidadeDev","aab", "88454");
			for (int i = 0; i < lista.size(); i++) {
				System.out.println(lista.get(i));
			}
		}

	}

}
