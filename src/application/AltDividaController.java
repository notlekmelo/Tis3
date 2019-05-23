package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AltDividaController {
	BancoDeDados db = new BancoDeDados();
	static Stage s1;

	@FXML
	private TextField value, dev, cli, func,numParc, forma, Valpay, data;

	@FXML
	private TextArea tipo,hist,histEdit;

	public void inicio() throws IOException {
		s1 = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("AltDivida.fxml"));
		Scene scene = new Scene(root);
		s1.setScene(scene);
		s1.setResizable(false);
		s1.show();
	}

	@FXML
	private void setValores(MouseEvent e) {
		setValores();
	}

	private void setValores() {
		db.conectar();
		if(db.estaConectado()){
			value.setText(db.getInfDiv("valorDiv", "iddivida"));
			tipo.setText(db.getInfDiv("tipoDiv","iddivida") + "  " + db.getInfDiv("numProc", "iddivida"));
			dev.setText(db.getInfDev("identidadeDev", "idDev"));
			cli.setText(db.getInfCli("identidadeCli", "idCli"));
			forma.setText(db.getInfDiv("formaPag", "iddivida"));
			func.setText(db.getInfuncionario("cpfFunc", "idFunc"));
			hist.setText(db.getInfHist());
			Valpay.setText("" + (Float.parseFloat(db.getInfParc("valorTotal")) - Float.parseFloat(db.getInfParc("valorPago"))));
			data.setText(db.getInfParc("dataParcela"));
			numParc.setText("" + db.getContParc());
		}
	}

	@FXML
	private void atualizarDivida(MouseEvent event) {
		db.conectar();
		if(db.estaConectado()){
			db.alterarDiv(func.getText(), db.getInfDiv("iddivida", "iddivida"));
			if(!histEdit.getText().equals("")) {
				db.atualizarHist(histEdit.getText());
				histEdit.clear();
				setValores();
			}
		}
	}

	@FXML
	private void savePay(MouseEvent e) {
		db.conectar();
		if(db.estaConectado()) {
			if(db.getInfunc("tipoFunc").equals("2")) {
				String mensagem;
				mensagem = "Pagamento da parcela de " + data.getText() + " R$ " + Valpay.getText() + " registrado por: ";
				db.atualizarPag(mensagem, -1);
				setValores();
			} else JOptionPane.showMessageDialog(null, "Você não tem permissão para alterar este campo");
		}
	}

	@FXML
	private void payParc(MouseEvent e) {
		db.conectar();
		if(db.estaConectado()) {
			if(db.getInfunc("tipoFunc").equals("2")) {
				String mensagem;
				float valor = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do pagamento realizado"));
				mensagem = "Pagamento parcial da parcela de "+ data.getText() + " de R$ " + valor + " registrado por: ";
				db.atualizarPag(mensagem, valor);
				setValores();
			} else JOptionPane.showMessageDialog(null, "Você não tem permissão para alterar este campo");
		}
	}

}
