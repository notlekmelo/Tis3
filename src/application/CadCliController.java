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

public class CadCliController implements Initializable{
	static BancoDeDados db = new BancoDeDados();
	static Stage s1;
	
	@FXML
    private TextField name,adress,email,tel,id;
	
	public void inicio() throws IOException {
		s1 = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CadCli.fxml"));
        Scene scene = new Scene(root);
        s1.setScene(scene);
        s1.setResizable(false);
        s1.show(); 

	}
	
	@FXML
	private void salvarCli(MouseEvent e) {
		db.conectar();
		if (db.estaConectado()) {
				if(db.inserirCliente(name.getText(), id.getText(), email.getText(), tel.getText(), adress.getText())) {
					JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
					s1.close();
				}
			else {
				JOptionPane.showMessageDialog(null, "Erro no cadastro");

			}
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
