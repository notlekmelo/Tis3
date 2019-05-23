package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;

public class CadRespController implements Initializable {
	static BancoDeDados db = new BancoDeDados();
	static Stage s1;
	
	@FXML
	private RadioButton um, dois;

	@FXML
	private ToggleGroup perm;
	
	@FXML
	private TextField nome, cpf, user;
	
	 @FXML
	    private PasswordField pass,confirm;

	public void inicio() throws IOException {
		s1 = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("CadResp.fxml"));
		Scene scene = new Scene(root);
		s1.setScene(scene);
		s1.setResizable(false);
		s1.show();

	}

	@FXML
	private void salvaResp(MouseEvent event) {
		try {
		int tipo = 0;
		if (perm.getSelectedToggle().equals(um)) {
			tipo = 0;
		} else if (perm.getSelectedToggle().equals(dois)){
			tipo = 1;
		}
		else {
			tipo = 2;
		}
		// Salva no banco novo funcionario
		// tipo ainda não implementado: 1 ou 2, será inserido manualmente até a
		// implemtentação do tipo
		db.conectar();
		if (db.estaConectado()) {
			if(pass.getText().equals(confirm.getText())) {

				if(db.inserirFuncionario(user.getText(), nome.getText(), pass.getText(), tipo, cpf.getText())) {
					JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
					s1.close();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "As senhas não coincidem...");
				pass.setText(" ");
				confirm.setText(" ");
			}
		}
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Favor preencha os campos corretamente");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}