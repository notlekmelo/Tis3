package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AltEntController {
	BancoDeDados db = new BancoDeDados();
	static String tipo;
	static Stage s1;

	@FXML
	private TextField name,adress,email,tel,id;


	public void inicio(String tipo) throws IOException {
		AltEntController.tipo = tipo;
		s1 = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("AltEnt.fxml"));
		Scene scene = new Scene(root);
		s1.setScene(scene);
		s1.setResizable(false);
		s1.show();
	}

	@FXML
	private void setValores(MouseEvent e) {
		db.conectar();
		if(db.estaConectado()) {
			if(tipo.equals("cliente")) {
				name.setText(db.getInfCli("nomeCli", "idCli"));
				adress.setText(db.getInfCli("endCli", "idCli"));
				email.setText(db.getInfCli("emailCli", "idCli"));
				tel.setText(db.getInfCli("telCli", "idCli"));
				id.setText(db.getInfCli("identidadeCli", "idCli"));
			}
			else {
				name.setText(db.getInfDev("nomeDev", "idDev"));
				adress.setText(db.getInfDev("endDev", "idDev"));
				email.setText(db.getInfDev("emailDev", "idDev"));
				tel.setText(db.getInfDev("telDev", "idDev"));
				id.setText(db.getInfDev("identidadeDev", "idDev"));
			}

		}
	}

	@FXML
	void salvarCli(MouseEvent e) {
		if(!name.getText().equals("") && !adress.getText().equals("") && !email.getText().equals("") && !tel.getText().equals("") && !id.getText().equals("")) {
			db.conectar();
			if(db.estaConectado()) {
				if(tipo.equals("cliente"))
					db.alterarCli(name.getText(), id.getText(), email.getText(), tel.getText(), adress.getText(), db.getInfCli("idCli", "idCli"));
				else
					db.alterarDev(name.getText(), id.getText(), email.getText(), tel.getText(), adress.getText(), db.getInfDev("idDev", "idDev"));
			}
		} else {
			JOptionPane.showMessageDialog(null,"Favor preencha todos os campos");
		}
	} 

	@FXML
	private void exit(MouseEvent e) throws IOException {
		OpcoesPesqController nova = new OpcoesPesqController();
		nova.inicio(tipo);
		s1.close();
	}
}
