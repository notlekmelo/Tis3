package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AltUser {
	static Stage s1;


	public void inicio() throws IOException {
		s1 = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("AltUser.fxml"));
		Scene scene = new Scene(root);
		s1.setScene(scene);
		s1.show();
	}

	@FXML
	private void altPerm(MouseEvent e) {
		BancoDeDados db = new BancoDeDados();
		db.conectar();
		if(db.estaConectado()){
			String cpf="";
			int novoTipo=0;
			cpf = JOptionPane.showInputDialog("Insira o CPF do funcionário");
			novoTipo = Integer.parseInt(JOptionPane.showInputDialog("Digite 0 para acesso básico\nDigite 1 para acesso intermediário"
					+ "\nDigite 2 para acesso avançado"));
			db.alterarTipo(cpf, novoTipo);
		}
		s1.close();
	}

	@FXML
	private void delUser(MouseEvent e) {
		BancoDeDados db = new BancoDeDados();
		db.conectar();
		if(db.estaConectado()){
			String cpf = JOptionPane.showInputDialog("Insira o CPF do funcionário a ser excluído");
			db.alterarTipo(cpf, -1);
		}
		s1.close();

	}


}
