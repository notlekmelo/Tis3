package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class CadastrosController implements Initializable {

	@FXML
	private void cadastrarResponsavel(MouseEvent e) throws IOException {
		BancoDeDados bd = new BancoDeDados();
		bd.conectar();
		if (bd.estaConectado()) {
			if (!bd.getInfunc("tipoFunc").equals("2")) {
				JOptionPane.showMessageDialog(null, "Você não tem permissão para acessar este campo");
			} else {
				CadRespController resp = new CadRespController();
				resp.inicio();
			}
		}
	}

	@FXML
	private void cadastrarClientes(MouseEvent e) throws IOException {
		BancoDeDados bd = new BancoDeDados();
		bd.conectar();
		if (bd.estaConectado()) {
				CadCliController cad = new CadCliController();
				cad.inicio();
		}

	}
	
	@FXML
	private void cadastrarDevedores(MouseEvent e) throws IOException {
		BancoDeDados bd = new BancoDeDados();
		bd.conectar();
		if (bd.estaConectado()) {
				CadDevController cad = new CadDevController();
				cad.inicio();
		}
	}

	@FXML
	private void cadastrarDivida(MouseEvent e) throws IOException {
		CadDividaController janela = new CadDividaController();
		janela.inicio();
	}

	@FXML
	private void goBack(MouseEvent event) {
		Main janela = new Main();
		janela.loadScene("Sample.fxml", "Tela principal");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
