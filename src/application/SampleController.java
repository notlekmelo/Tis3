package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;


public class SampleController implements Initializable{
	

	@FXML
	private Label userLog = new Label();

	@FXML
    void writeUser(MouseEvent event) throws SQLException {
	// Quando essa página for aberta a guia de texto deve escrever o nome do usuário logado
		BancoDeDados bd = new BancoDeDados();
		bd.conectar();
		String op;
		if(bd.estaConectado()) {
			op = bd.getInfunc("nomeFunc");
			userLog.setText(op);
		}
		
    }

	@FXML
	private void cadastros(MouseEvent event){
		Main janela = new Main();
		janela.loadScene("Cadastros.fxml","Cadastros");
	}

	@FXML
	private void pesquisas(MouseEvent event){
		Main janela = new Main();
		janela.loadScene("Pesquisar.fxml","Pesquise aqui");
	}

	@FXML
	private void exit(MouseEvent event) {
		//Sair da conexão
		Main janela = new Main();
		janela.loadScene("Login.fxml","Bem vindo!");
	}

	@FXML
	private void config(MouseEvent event) {
		Main janela = new Main();
		janela.loadScene("Config.fxml","Configurações");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}

