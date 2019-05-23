package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class CadDividaController implements Initializable {
	private static Stage s1;
	private ArrayList<TextField> valoresParc = new ArrayList<TextField>();
	private ArrayList<TextField> datasParc = new ArrayList<TextField>();

	@FXML
	private TextField value, numProc, dev, cli, func, numParc;

	@FXML
	Pane mainPane;

	@FXML
	private Label label;

	@FXML
	private ToggleGroup tipo, forma;

	@FXML
	private RadioButton um, dois, bb, cc, ted;

	public void inicio() throws IOException {
		s1 = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("CadDivida.fxml"));
		Scene scene = new Scene(root);
		s1.setScene(scene);
		s1.setResizable(false);
		s1.show();
	}

	@FXML
	private void regDivida(MouseEvent event) {
		// talvez tenhamos que passar o valor vazio caso não tenha escrito nada em
		// numProc, ao invés de pegar direto;
		BancoDeDados bd = new BancoDeDados();
		bd.conectar();
		if (bd.estaConectado()) {
			String formaPag = "", tipoAcordo = "";
			if (forma.getSelectedToggle().equals(bb))
				formaPag = "Boleto Bancário";
			else if (forma.getSelectedToggle().equals(cc))
				formaPag = "Cartão de Crédito";
			else
				formaPag = "Transferência Bancária";
			if (tipo.getSelectedToggle().equals(um))
				tipoAcordo = "Extrajudicial";
			else
				tipoAcordo = "Judicial";
			ArrayList<String> valores = new ArrayList<String>();
			ArrayList<String> datas = new ArrayList<String>();
			while(valoresParc.size()>0) {
				valores.add(valoresParc.remove(0).getText());
				datas.add(datasParc.remove(0).getText());
			}
			if (bd.inserirDivida(value.getText(), formaPag, tipoAcordo, numProc.getText(),
					func.getText(), dev.getText(), cli.getText(), valores, datas)) {
				JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
				s1.close();
			} else {
				JOptionPane.showMessageDialog(null, "Erro no cadastro");
			}
		}
	}

	@FXML
	private void pesquisar(MouseEvent e) {
		s1.close();
		Main janela = new Main();
		janela.loadScene("Pesquisar.fxml","Pesquise aqui");
	}

	@FXML
	private void show(MouseEvent e) {
		label.setVisible(true);
		numProc.setVisible(true);
	}

	@FXML
	private void hide(MouseEvent e) {
		label.setVisible(false);
		numProc.setVisible(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		numParc.setOnKeyPressed(new EventHandler<Event>() {
			@Override  
			public void handle(Event e) {
				if(numParc.getText().length()>0) {
					int qtd = Integer.parseInt(numParc.getText());
					for(int i = 0; i < qtd; i++) {
						Label data = new Label("Data da parcela " + (i+1) + ":");
						data.setTextFill(Paint.valueOf("white"));
						data.setLayoutY(81 + (i*52));
						data.setLayoutX(877);
						Label valor = new Label("Valor da parcela " + (i+1)+ ":");
						valor.setTextFill(Paint.valueOf("white"));
						valor.setLayoutY(81 + (i*52));
						valor.setLayoutX(596);
						TextField valParc = new TextField();
						valParc.setLayoutY(81 + (i*52));
						valParc.setLayoutX(731);
						valParc.setPrefWidth(131);
						valParc.setPrefHeight(31);
						valParc.setId("valParc" + (i+1));
						TextField dataParc = new TextField();
						dataParc.setLayoutY(81 + (i*52));
						dataParc.setLayoutX(1009);
						dataParc.setId("dataParc" + (i+1));
						dataParc.setPromptText("dd/mm/aaaa");
						dataParc.setPrefWidth(164);
						dataParc.setPrefHeight(31);
						valParc.setText("" + (Float.parseFloat(value.getText())/Float.parseFloat(numParc.getText())));
						if(i>16) {
							data.setLayoutX(1532);
							data.setLayoutY(81 + ((i-17)*52));
							valor.setLayoutX(1251);
							valor.setLayoutY(81 + ((i-17)*52));
							valParc.setLayoutX(1386);
							valParc.setLayoutY(81 + ((i-17)*52));
							dataParc.setLayoutX(1664);
							dataParc.setLayoutY(81 + ((i-17)*52));
						}
						mainPane.getChildren().add(valParc);
						mainPane.getChildren().add(dataParc);
						mainPane.getChildren().add(data);
						mainPane.getChildren().add(valor);
						valoresParc.add(valParc);
						datasParc.add(dataParc);
					}
				}
			}
		});
	}

}