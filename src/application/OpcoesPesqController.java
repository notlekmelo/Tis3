package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OpcoesPesqController {
	static Stage s2;
	static String tipo;
	
	public void inicio(String tipo) throws IOException {
		OpcoesPesqController.tipo = tipo;
		s2 = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("OpcoesPesq.fxml"));
		Scene scene = new Scene(root);
		s2.setScene(scene);
		s2.setResizable(false);
		s2.show();
	}
	
	@FXML
    private void alterar(MouseEvent event) throws IOException {
		if(tipo.equals("divida")) {
			AltDividaController nova = new AltDividaController();
			nova.inicio();
		}
		else {
		AltEntController nova = new AltEntController();
		nova.inicio(tipo);
		}
		s2.close();
    }

    @FXML
    private void listar(MouseEvent event) {

    }

    @FXML
    private void relatar(MouseEvent event) {

    }

    @FXML
    private void exit(MouseEvent e) {
    	s2.close();
    }
    
}
