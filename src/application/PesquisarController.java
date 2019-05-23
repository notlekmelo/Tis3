package application;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PesquisarController {

	@FXML
	private Button buscar;

	@FXML
	private TextField search, search1, dia,mes,ano;

	@FXML
	private TextArea encontrados;

	@FXML
	private ToggleGroup peloQue, qualPesq;

	@FXML
	private RadioButton cli, dev, divida, nome, ident, value, data, identificador;

	@FXML
	private void goBack(MouseEvent e) {
		Main janela = new Main();
		janela.loadScene("Sample.fxml", "Tela Principal");
	}

	@FXML
	private void search() {
		try {
			String tipo = "";
			String tipo1 = "";
			if (qualPesq.getSelectedToggle().equals(cli)) {
				if (peloQue.getSelectedToggle().equals(nome)) {
					tipo = "nomeCli";
				} else if (peloQue.getSelectedToggle().equals(ident)) {
					tipo = "identidadeCli";
				} else if (peloQue.getSelectedToggle().equals(identificador))
					tipo = "idCli";
				pesqCli(tipo);
			} else if (qualPesq.getSelectedToggle().equals(dev)) {
				if (peloQue.getSelectedToggle().equals(nome)) {
					tipo = "nomeDev";
				} else if (peloQue.getSelectedToggle().equals(ident)) {
					tipo = "identidadeDev";
				} else if (peloQue.getSelectedToggle().equals(identificador))
					tipo = "idDev";
				pesqDev(tipo);
			} else {
				if (peloQue.getSelectedToggle().equals(data))
					tipo = "data";
				else if (peloQue.getSelectedToggle().equals(value))
					tipo = "valorDiv";
				else if (peloQue.getSelectedToggle().equals(identificador))
					tipo = "iddivida";
				else if (peloQue.getSelectedToggle().equals(nome)) {
					tipo = "nomeCli";
					tipo1 = "nomeDev";
				} else if (peloQue.getSelectedToggle().equals(ident)) {
					tipo = "identidadeCli";
					tipo1 = "identidadeDev";
				}
				pesqDivida(tipo, tipo1);
			}
			// código abaixo só deve ser executado ao encontrar um resultado
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Favor selecionar o tipo de pesquisa e pelo que deseja pesquisar");
		}
	}

	@FXML
	private void show(MouseEvent e) {
		search1.setVisible(true);
		data.setVisible(true);
		dia.setVisible(true);
		ano.setVisible(true);
		mes.setVisible(true);
		value.setVisible(true);
	}

	@FXML
	private void hide(MouseEvent e) {
		search1.setVisible(false);
		data.setVisible(false);
		dia.setVisible(false);
		ano.setVisible(false);
		mes.setVisible(false);
		value.setVisible(false);
	}

	private void pesqCli(String tipo) {
		BancoDeDados db = new BancoDeDados();
		db.conectar();
		if (db.estaConectado()) {
			ArrayList<String> lista = db.listarClientes(tipo, search.getText());
			int tam = lista.size();
			for (int i = 0; i < tam; i++)
				encontrados.appendText(db.listarClientes(tipo, search.getText()).get(i) + "\n\n");
			if (tam == 1
					&& (peloQue.getSelectedToggle().equals(identificador) || peloQue.getSelectedToggle().equals(ident))
					&& !encontrados.getText().equals("")) {
				buscar.setText(search.getText());
				buscar.setVisible(true);
			}
		}
	}

	private void pesqDev(String tipo) {
		BancoDeDados db = new BancoDeDados();
		db.conectar();
		if (db.estaConectado()) {
			ArrayList<String> lista = db.listarDevedores(tipo, search.getText());
			int tam = lista.size();
			for (int i = 0; i < tam; i++)
				encontrados.appendText(lista.get(i) + "\n\n");
			if (tam == 1
					&& (peloQue.getSelectedToggle().equals(identificador) || peloQue.getSelectedToggle().equals(ident))
					&& !encontrados.getText().equals("")) {
				buscar.setText(search.getText());
				buscar.setVisible(true);
			}
		}
	}

	private void pesqDivida(String tipo, String tipo1) {
		BancoDeDados db = new BancoDeDados();
		db.conectar();
		if (db.estaConectado()) {
			if (tipo.equals("identidadeCli") && tipo1.equals("identidadeDev")) {
				ArrayList<String> lista = db.listarDividaCruzada(tipo, tipo1, search.getText(), search1.getText());
				int tam = lista.size();
				for (int i = 0; i < tam; i++)
					encontrados.appendText(lista.get(i) + "\n\n");
				if (tam == 1 && (peloQue.getSelectedToggle().equals(identificador)
						|| peloQue.getSelectedToggle().equals(ident)) && !encontrados.getText().equals("")) {
					buscar.setText(search.getText());
					buscar.setVisible(true);
				}
			} else if (tipo.equals("nomeCli") && tipo1.equals("nomeDev")) {
				ArrayList<String> lista = db.listarDividaCruzada(tipo, tipo1, search.getText(), search1.getText());
				int tam = lista.size();
				for (int i = 0; i < tam; i++)
					encontrados.appendText(lista.get(i) + "\n\n");
			} else {
				ArrayList<String> lista;
				if(peloQue.getSelectedToggle().equals(data)) {
					String date = dia.getText() + "/" + mes.getText() + "/" + ano.getText();
					lista = db.listarData("dataParcela", date);
				}
				else {
					lista = db.listarDivida(tipo, search.getText());
				}
				int tam = lista.size();
				for (int i = 0; i < tam; i++)
					encontrados.appendText(lista.get(i) + "\n\n");
				if (tam == 1 && (peloQue.getSelectedToggle().equals(identificador)
						|| peloQue.getSelectedToggle().equals(ident)) && !encontrados.getText().equals("")) {
					buscar.setText(search.getText());
					buscar.setVisible(true);
				}
			}

		}
	}

	@FXML
	private void selecionar(MouseEvent e) throws IOException {
		BancoDeDados db = new BancoDeDados();
		db.conectar();
		String tipo = "";
		if (db.estaConectado()) {
			String op = buscar.getText();
			if (qualPesq.getSelectedToggle().equals(cli)) {
				BancoDeDados.setSecCli(op);
				if (peloQue.getSelectedToggle().equals(ident)) {
					BancoDeDados.setSecCli(db.getInfCli("idCli", "identidadeCli"));
				} else if (peloQue.getSelectedToggle().equals(identificador)) {
					db.getInfCli("idCli", "idCli");
				}
				tipo = "cliente";
			} else if (qualPesq.getSelectedToggle().equals(dev)) {
				BancoDeDados.setSecDev(op);
				if (peloQue.getSelectedToggle().equals(ident)) {
					BancoDeDados.setSecDev(db.getInfDev("idDev", "identidadeDev"));
				} else if (peloQue.getSelectedToggle().equals(identificador)) {
					db.getInfDev("idDev", "idDev");
				}
				tipo = "devedor";
			} else if (qualPesq.getSelectedToggle().equals(divida)) {
				BancoDeDados.setSecDiv(op);
				if (peloQue.getSelectedToggle().equals(identificador))
					BancoDeDados.setSecDiv(db.getInfDiv("iddivida", "iddivida"));
					BancoDeDados.setSecFunc(db.getInfDiv("idFunc", "iddivida"));
				tipo = "divida";
			}
			OpcoesPesqController janela = new OpcoesPesqController();
			janela.inicio(tipo);
		}
	}

	@FXML
	private void enter(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			buscar.setVisible(false);
			encontrados.clear();
			search();
		}
	}

	@FXML
	private void clique(MouseEvent e) {
		buscar.setVisible(false);
		encontrados.clear();
		search();
	}

}
