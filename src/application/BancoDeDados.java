package application;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BancoDeDados {

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultset = null;
	private static int logado;
	private static String secDev, secCli, secDiv, secFunc;

	public void conectar() {
		String servidor = "jdbc:mysql://localhost:3306/tis3?useTimezone=true&serverTimezone=UTC";
		String usuario = "root";
		String senha = "";
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driver);
			this.connection = DriverManager.getConnection(servidor, usuario, senha);
			this.statement = this.connection.createStatement();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public boolean estaConectado() {
		if (this.connection != null)
			return true;
		else
			return false;
	}

	public String getInfunc(String op1) {
		try {
			String retorno;
			this.statement = this.connection.createStatement();
			String querry = "SELECT * from funcionario where idFunc = " + logado + ";";
			this.resultset = this.statement.executeQuery(querry);
			if (this.resultset.next()) {
				retorno = this.resultset.getString(op1);
				return retorno;
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public String getInfuncionario(String op1, String op2) {
		try {
			String retorno;
			this.statement = this.connection.createStatement();
			String querry = "SELECT * from funcionario where " + op2 + " = " + secFunc + ";";
			this.resultset = this.statement.executeQuery(querry);
			if (this.resultset.next()) {
				retorno = this.resultset.getString(op1);
				return retorno;
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public String getInfDev(String op1, String op2) {
		try {
			String retorno;
			this.statement = this.connection.createStatement();
			String querry = "SELECT * from devedor where " + op2 + " = '" + secDev + "';";
			this.resultset = this.statement.executeQuery(querry);
			if (this.resultset.next()) {
				retorno = this.resultset.getString(op1);
				return retorno;
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public String getInfCli(String op1, String op2) {
		try {
			String retorno;
			this.statement = this.connection.createStatement();
			String querry = "SELECT * from cliente where " + op2 + " = '" + secCli + "';";
			this.resultset = this.statement.executeQuery(querry);
			if (this.resultset.next()) {
				retorno = this.resultset.getString(op1);
				return retorno;
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public String getInfHist() {
		try {
			String retorno = "";
			this.statement = this.connection.createStatement();
			String querry = "SELECT * from historico where idDivida = "
					+ Integer.parseInt(getInfDiv("iddivida", "iddivida")) + ";";
			this.resultset = this.statement.executeQuery(querry);
			while (this.resultset.next()) {
				retorno = retorno + this.resultset.getString("mensagem") + " em " + this.resultset.getString("dataHist")
						+ "\n";
			}
			return retorno;

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public String getInfParc(String op) {
		try {
			String retorno = "";
			this.statement = this.connection.createStatement();
			String querry = "SELECT * from parcela where divAssoc = " + getInfDiv("iddivida", "iddivida") + ";";
			this.resultset = this.statement.executeQuery(querry);
			while (this.resultset.next())
				if (!this.resultset.getString("status").equals("Total")) {
					if (retorno.equals(""))
						retorno = this.resultset.getString(op);
				}
			return retorno;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public int getContParc() {
		try {
			int cont = 0;
			this.statement = this.connection.createStatement();
			String querry = "SELECT * from parcela where divAssoc = " + getInfDiv("iddivida", "iddivida") + ";";
			this.resultset = this.statement.executeQuery(querry);
			while (this.resultset.next()) {
				if (!this.resultset.getString("status").equals("Total")) {
					cont++;
				}
			}
			return cont;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return 0;
	}

	public String getInfDiv(String op1, String op2) {
		try {
			String retorno;
			this.statement = this.connection.createStatement();
			String querry = "SELECT * from divida where " + op2 + " = " + secDiv + ";";
			this.resultset = this.statement.executeQuery(querry);
			if (this.resultset.next()) {
				retorno = this.resultset.getString(op1);
				return retorno;
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<String> listarClientes(String coluna, String valor) {
		try {
			ArrayList<String> result = new ArrayList<String>();
			String querry = "SELECT * from cliente order by idCli";
			this.resultset = this.statement.executeQuery(querry);
			this.statement = this.connection.createStatement();
			while (this.resultset.next()) {
				if (this.resultset.getString("" + coluna + "").contains(valor))
					result.add("ID: " + this.resultset.getString("idCli") + "\t\tNome: "
							+ this.resultset.getString("nomeCli") + "\t\tCPF/CNPJ: "
							+ this.resultset.getString("identidadeCli") + "\t\tTelefone: "
							+ this.resultset.getString("telCli") + "\t\tE-mail: "
							+ this.resultset.getString("emailCli"));
			}
			return result;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<String> listarDevedores(String coluna, String valor) {
		try {
			ArrayList<String> result = new ArrayList<String>();
			String querry = "SELECT * from devedor order by idDev";
			this.resultset = this.statement.executeQuery(querry);
			this.statement = this.connection.createStatement();
			while (this.resultset.next()) {
				if (this.resultset.getString("" + coluna + "").contains(valor))
					result.add("ID: " + this.resultset.getString("idDev") + "\t\tNome: "
							+ this.resultset.getString("nomeDev") + "\t\tCPF/CNPJ: "
							+ this.resultset.getString("identidadeDev") + "\t\tTelefone: "
							+ this.resultset.getString("telDev") + "\t\tE-mail: "
							+ this.resultset.getString("emailDev"));
			}
			return result;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public String BuscaID(String tabela, String id, String coluna, String valor) {
		try {
			String querry = "SELECT * from " + tabela + " order by " + coluna;
			this.resultset = this.statement.executeQuery(querry);
			this.statement = this.connection.createStatement();
			while (this.resultset.next()) {
				if (this.resultset.getString(coluna).contains(valor))
					return this.resultset.getString(id);
			}
			return null;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<String> listarNomesCli(String coluna, String valor) {
		try {
			ArrayList<String> result = new ArrayList<String>();
			String querry = "SELECT * from cliente";
			this.resultset = this.statement.executeQuery(querry);
			this.statement = this.connection.createStatement();
			while (this.resultset.next()) {
				if (this.resultset.getString("" + coluna + "").equals(valor))
					result.add(this.resultset.getString("idCli"));
			}
			return result;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<String> listarNomesDev(String coluna, String valor) {
		try {
			ArrayList<String> result = new ArrayList<String>();
			String querry = "SELECT * from devedor";
			this.resultset = this.statement.executeQuery(querry);
			this.statement = this.connection.createStatement();
			while (this.resultset.next()) {
				if (this.resultset.getString("" + coluna + "").equals(valor))
					result.add(this.resultset.getString("idDev"));
			}
			return result;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<String> listarDividaCruzada(String coluna1, String coluna2, String cli, String dev) {
		try {
			ArrayList<String> result = new ArrayList<String>();
			String querry = "SELECT divida.*, cliente.*, devedor.* from divida inner join cliente on divida.idCli = cliente.idCli inner join devedor on divida.idDev = devedor.idDev;";
			this.resultset = this.statement.executeQuery(querry);
			this.statement = this.connection.createStatement();
			while (this.resultset.next()) {
				if (this.resultset.getString("" + coluna1 + "").contains(cli)
						&& this.resultset.getString("" + coluna2 + "").contains(dev)) {
					result.add("ID: " + this.resultset.getString("iddivida") + "\t\tValor: R$"
							+ this.resultset.getString("valorDiv") + "\t\tForma de pagamento: "
							+ this.resultset.getString("formaPag") + "\t\tCliente: "
							+ this.resultset.getString("" + coluna1 + "") + "\t\tDevedor: "
							+ this.resultset.getString("" + coluna2 + ""));
				}
			}
			return result;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<String> listarDivida(String coluna, String valor) {
		try {
			ArrayList<String> result = new ArrayList<String>();
			String querry = "SELECT * from divida order by iddivida";
			this.resultset = this.statement.executeQuery(querry);
			this.statement = this.connection.createStatement();
			while (this.resultset.next()) {
				if (this.resultset.getString("" + coluna + "").equals(valor)) {
					setSecCli(this.resultset.getString("idCli"));
					setSecDev(this.resultset.getString("idDev"));
					result.add("ID: " + this.resultset.getString("iddivida") + "\t\tValor: "
							+ this.resultset.getString("valorDiv") + "\t\tForma de pagamento: "
							+ this.resultset.getString("formaPag") + "\t\tCliente: " + getInfCli("nomeCli", "idCli")
							+ "\t\tDevedor: " + getInfDev("nomeDev", "idDev"));
				}
			}
			return result;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<String> listarData(String coluna, String valor) {
		try {
			ArrayList<String> result = new ArrayList<String>();
			String querry = "SELECT * from parcela order by idparcela";
			this.resultset = this.statement.executeQuery(querry);
			this.statement = this.connection.createStatement();
			while (this.resultset.next()) {
				if (this.resultset.getString("" + coluna + "").equals(valor)) {
					setSecDiv(this.resultset.getString("divAssoc"));
					setSecCli(getInfDiv("idCli", "idDivida"));
					setSecDev(getInfDiv("idDev", "idDivida"));
					result.add("ID: " + getInfDiv("iddivida", "idDivida") + "\t\tValor: "
							+ getInfDiv("valorDiv", "idDivida") + "\t\tForma de pagamento: "
							+ getInfDiv("formaPag", "idDivida") + "\t\tCliente: " + getInfCli("nomeCli", "idCli")
							+ "\t\tDevedor: " + getInfDev("nomeDev", "idDev"));

				}
			}
			return result;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return null;
	}

	public boolean login(String user, String senha) {
		try {
			this.statement = this.connection.createStatement();
			String querry = "SELECT * from funcionario where usuario = '" + user + "' and senhaFunc = '" + senha + "';";
			this.resultset = this.statement.executeQuery(querry);
			if (this.resultset.next()) {
				setLogado(this.resultset.getString("idFunc"));
				return true;
			} else
				JOptionPane.showMessageDialog(null, "Usuáro ou senha inválidos");
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return false;
	}

	public boolean inserirCliente(String nome, String identidade, String email, String tel, String endereco) {
		try {
			String querry = "insert into cliente (nomeCli, identidadeCli, emailCli, telCli, endCli) values ('" + nome
					+ "', '" + identidade + "', '" + email + "', '" + tel + "', '" + endereco + "');";
			this.statement.executeUpdate(querry);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Já existe este cliente.");
			return false;
		}
	}

	public boolean inserirDivida(String valor, String formaPag, String tipo, String numProc, String cpfFunc,
			String cpfDev, String cpfCli, ArrayList<String> valorParc, ArrayList<String> data) {
		try {
			String idCli = BuscaID("cliente", "idCli", "identidadeCli", cpfCli);
			String idDev = BuscaID("devedor", "idDev", "identidadeDev", cpfDev);
			String idFunc = BuscaID("funcionario", "idFunc", "cpfFunc", cpfFunc);

			if (!idCli.equals(null) && !idDev.equals(null) && !idFunc.equals(null)) {
				String querry = "insert into divida (valorDiv, formaPag, tipoDiv, numProc, idFunc, idDev, idCli) values ('"
						+ valor + "', '" + formaPag + "', '" + tipo + "', '" + numProc + "', '" + idFunc + "', '"
						+ idDev + "', '" + idCli + "');";
				this.statement.executeUpdate(querry);
				String ordena = "SELECT * from divida order by iddivida";
				this.resultset = this.statement.executeQuery(ordena);
				this.statement = this.connection.createStatement();
				String idDiv = "";
				while (this.resultset.next()) {
					if (this.resultset.isLast())
						idDiv = this.resultset.getString("iddivida");
				}
				addParcelas(idDiv, valorParc, data);
				return true;
			}
			return false;
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "CPF do funcionario ou do cliente ou do devedor está errado");
			return false;
		} catch (Exception erro) {
			System.out.println("Erro:" + erro.getMessage());
			return false;
		}
	}

	private void addParcelas(String idDiv, ArrayList<String> valor, ArrayList<String> data) throws SQLException {
		while (valor.size() > 0) {
			// não esta sendo executado após mudança do cod de onde aparece.
			String parc = "insert into parcela (divAssoc, dataParcela, valorPago, status, valorTotal) values ('" + idDiv
					+ "', '" + data.remove(0) + "' , '" + "0" + "', '" + "Pendente" + "', '" + valor.remove(0) + "');";
			this.statement.executeUpdate(parc);
		}
	}

	public boolean inserirDevedor(String nome, String identidade, String email, String tel, String endereco) {
		try {
			String querry = "insert into devedor (nomeDev, emailDev, telDev, identidadeDev, endDev) values ('" + nome
					+ "', '" + email + "', '" + tel + "', '" + identidade + "', '" + endereco + "');";
			this.statement.executeUpdate(querry);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Já existe este devedor.");
			return false;
		}
	}

	public boolean inserirFuncionario(String user, String nome, String senha, int tipo, String cpf) {
		try {
			String querry = "insert into funcionario (nomeFunc, senhaFunc, tipoFunc, cpfFunc, usuario) values ('" + nome
					+ "', '" + senha + "', '" + tipo + "', '" + cpf + "', '" + user + "');";
			this.statement.executeUpdate(querry);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Já existe este usuário.");
			return false;
		}
	}

	public void alterarCli(String nome, String identidade, String email, String tel, String end, String id) {
		try {
			String querry = "update cliente set nomeCli = '" + nome + "', identidadeCli = '" + identidade
					+ "', emailCli = '" + email + "', telCli = '" + tel + "', endCli = '" + end + "'  where idCli = "
					+ id + ";";
			this.statement.executeUpdate(querry);
			JOptionPane.showMessageDialog(null, "Atualizado com sucesso");
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao atualizar");
		}
	}

	public void alterarDev(String nome, String identidade, String email, String tel, String end, String id) {
		try {
			String querry = "update devedor set nomeDev = '" + nome + "', identidadeDev = '" + identidade
					+ "', emailDev = '" + email + "', telDev = '" + tel + "', endDev = '" + end + "'  where idDev = "
					+ id + ";";
			this.statement.executeUpdate(querry);
			JOptionPane.showMessageDialog(null, "Atualizado com sucesso");
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao atualizar");
		}
	}

	public void alterarDiv(String cpfFunc, String id) {
		try {
			String idFunc = BuscaID("funcionario", "idFunc", "cpfFunc", cpfFunc);
			if (!idFunc.equals(null)) {
				String querry = "update divida set idFunc = '" + idFunc + "'  where iddivida = " + id + ";";
				this.statement.executeUpdate(querry);
				JOptionPane.showMessageDialog(null, "Atualizado com sucesso");
			} else
				JOptionPane.showMessageDialog(null, "CPF do funcionário incorreto");
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao atualizardados da dívida");
		}
	}

	public void atualizarPag(String mensagem, float valor) {
		try {
			LocalDate hoje = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			if (valor < 0) {
				String querry = "update parcela set status = 'Total'" + " where idparcela = " + getInfParc("idparcela")
						+ ";";
				this.statement.executeUpdate(querry);
			} else {
				String querry = "update parcela set status = 'Parcial' , valorPago = '" + valor + "' where idparcela = "
						+ getInfParc("idparcela") + ";";
				this.statement.executeUpdate(querry);
			}
			String querry = "insert into historico (dataHist, mensagem, tipoHist, idDivida) values ('"
					+ hoje.format(formatter) + "', '" + (mensagem + getInfunc("usuario")) + "', " + 2 + ", '"
					+ getInfDiv("iddivida", "iddivida") + "');";
			this.statement.executeUpdate(querry);
			JOptionPane.showMessageDialog(null, "Pagamento salvo com sucesso");
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao atualizar pagamento");
		}
	}

	public void atualizarHist(String mensagem) {
		try {
			LocalDate hoje = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String querry = "insert into historico (dataHist, mensagem, tipoHist, idDivida) values ('"
					+ hoje.format(formatter) + "', '" + (mensagem + " - Alterado por " + getInfunc("usuario")) + "', "
					+ 1 + ", '" + getInfDiv("iddivida", "iddivida") + "');";
			this.statement.executeUpdate(querry);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao atualizar histórico");
		}
	}

	public void alterarSenha(String user, String novaSenha) {
		try {
			String querry = "update funcionario set senhaFunc = '" + novaSenha + "' where usuario = '" + user + "';";
			this.statement.executeUpdate(querry);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public void alterarTipo(String cpf, int novoTipo) {
		try {
			String querry = "update funcionario set tipoFunc = '" + novoTipo + "' where cpfFunc = '" + cpf + "';";
			this.statement.executeUpdate(querry);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public void gerarPdfCli(String id) {
		try {
//			this.statement = this.connection.createStatement();
//			String parc = "SELECT * from parcela inner join divida on parcela.divAssoc = divida.iddivida where idCli = "+id+"";
//			this.resultset = this.statement.executeQuery(parc);
//			ArrayList<String> result = new ArrayList<String>();
//			while (this.resultset.next())
//				result.add("Valor total: R$" + this.resultset.getString("valorTotal") + "----Valor pago: R$"
//						+ this.resultset.getString("valorPago") + "-----Status: " + this.resultset.getString("status"));
//			int tam = result.size();
			this.statement = this.connection.createStatement();
			String querry = "select * from cliente where idCli ="+id+";";
			this.resultset = this.statement.executeQuery(querry);
			Document document = new Document();
			PdfWriter.getInstance(document,
					new FileOutputStream(""+System.getProperty("user.home")+"\\Desktop\\Cliente--" + id + ".pdf"));
			document.open();
			Image image = Image.getInstance("cabecalho.png");
			image.setAlignment(Element.ALIGN_CENTER);
			document.add(image);
			PdfPTable tabela = new PdfPTable(1);
			PdfPCell cabecalho = new PdfPCell(new Paragraph("Cliente"));
			PdfPCell parcela = new PdfPCell(new Paragraph("Parcelas"));
			PdfPCell divida = new PdfPCell(new Paragraph("Dívida"));
			divida.setHorizontalAlignment(Element.ALIGN_CENTER);
			this.resultset.beforeFirst();
			if (this.resultset.next()) {
				cabecalho.setHorizontalAlignment(Element.ALIGN_CENTER);
				parcela.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabela.addCell(cabecalho);
				tabela.addCell("ID cliente: " + this.resultset.getString("idCli") + "");
				tabela.addCell("Identidade cliente: " + this.resultset.getString("identidadeCli") + "");
				tabela.addCell("Nome cliente: " + this.resultset.getString("nomeCli") + "");
				tabela.addCell("Email cliente: " + this.resultset.getString("emailCli") + "");
				tabela.addCell("Telefone cliente: " + this.resultset.getString("telCli") + "");
				tabela.addCell("Endereco cliente: " + this.resultset.getString("endCli") + "");
			}
			this.statement = this.connection.createStatement();
			String div = "select cliente.*, devedor.*, divida.*, funcionario.nomeFunc from divida \r\n"
					+ "inner join cliente on divida.idCli = cliente.idCli\r\n"
					+ "inner join devedor on divida.idDev = devedor.idDev\r\n"
					+ "inner join funcionario on divida.idFunc = funcionario.idFunc\r\n" + "where cliente.idCli =" + id
					+ ";";
			this.resultset = this.statement.executeQuery(div);

			this.resultset.beforeFirst();
			while (this.resultset.next()) {
				tabela.addCell(divida);
				tabela.addCell("ID dívida: " + this.resultset.getString("iddivida") + "");
				tabela.addCell("Responsável: " + this.resultset.getString("nomeFunc") + "");
				tabela.addCell("Devedor: " + this.resultset.getString("nomeDev") + "");
				tabela.addCell("Tipo da dívida: " + this.resultset.getString("tipoDiv") + "");
				tabela.addCell("Forma de pagamento: " + this.resultset.getString("formaPag") + "");
				tabela.addCell("Valor total: " + this.resultset.getString("valorDiv") + "");
//				if (tam > 0) {
//					tabela.addCell("Número de parcelas: " + tam + "");
//					tabela.addCell(parcela);
//					for (int i = 0; i < result.size(); i++)
//						tabela.addCell("" + result.get(i) + "");
//				}
			}
			document.add(tabela);
			document.close();
		} catch (SQLException | DocumentException | IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public void gerarPdfDiv(String id) {
		try {
			this.statement = this.connection.createStatement();
			String parc = "SELECT * from parcela where divAssoc =" + id + ";";
			this.resultset = this.statement.executeQuery(parc);
			ArrayList<String> result = new ArrayList<String>();
			while (this.resultset.next())
				result.add("Valor total: R$" + this.resultset.getString("valorTotal") + "----Valor pago: R$"
						+ this.resultset.getString("valorPago") + "-----Status: " + this.resultset.getString("status"));
			int tam = result.size();
			this.statement = this.connection.createStatement();
			String querry = "SELECT divida.*, funcionario.nomeFunc, cliente.nomeCli, devedor.nomeDev from divida \r\n" + 
					"inner join funcionario on divida.idFunc = funcionario.idFunc  \r\n" + 
					"inner join cliente on divida.idCli = cliente.idCli  \r\n" + 
					"inner join devedor on divida.idDev = devedor.idDev  \r\n" + 
					"where divida.iddivida="+ id + ";";
			this.resultset = this.statement.executeQuery(querry);
			Document document = new Document();
			PdfWriter.getInstance(document,
					new FileOutputStream(""+System.getProperty("user.home")+"\\Desktop\\Divida--" + id + ".pdf"));
			document.open();
			Image image = Image.getInstance("cabecalho.png");
			image.setAlignment(Element.ALIGN_CENTER);
			document.add(image);
			if (this.resultset.next()) {
				PdfPTable tabela = new PdfPTable(1);
				PdfPCell divida = new PdfPCell(new Paragraph("Dívida"));
				PdfPCell parcela = new PdfPCell(new Paragraph("Parcelas"));
				PdfPCell historico = new PdfPCell(new Paragraph("Histórico"));
				divida.setHorizontalAlignment(Element.ALIGN_CENTER);
				parcela.setHorizontalAlignment(Element.ALIGN_CENTER);
				historico.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabela.addCell(divida);
				tabela.addCell("ID dívida: " + this.resultset.getString("iddivida") + "");
				tabela.addCell("Responsável: " + this.resultset.getString("nomeFunc") + "");
				tabela.addCell("Cliente: " + this.resultset.getString("nomeCli") + "");
				tabela.addCell("Devedor: " + this.resultset.getString("nomeDev") + "");
				tabela.addCell("Tipo da dívida: " + this.resultset.getString("tipoDiv") + "");
				tabela.addCell("Forma de pagamento: " + this.resultset.getString("formaPag") + "");
				tabela.addCell("Valor total: " + this.resultset.getString("valorDiv") + "");
				if (tam > 0) {
					tabela.addCell("Número de parcelas: " + tam + "");
					tabela.addCell(parcela);
					for (int i = 0; i < result.size(); i++)
						tabela.addCell("" + result.get(i) + "");
				}
				this.statement = this.connection.createStatement();
				String hist = "SELECT * from historico where idDivida =" + id + ";";
				this.resultset = this.statement.executeQuery(hist);
				tabela.addCell(historico);
				this.resultset.beforeFirst();
				while (this.resultset.next())
					tabela.addCell(this.resultset.getString("mensagem") + "");
				document.add(tabela);
			}
			document.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public void gerarPdfDev(String id) {
		try {
//			this.statement = this.connection.createStatement();
//			String parc = "SELECT * from parcela inner join divida on parcela.divAssoc = divida.iddivida where idCli = "+id+"";
//			this.resultset = this.statement.executeQuery(parc);
//			ArrayList<String> result = new ArrayList<String>();
//			while (this.resultset.next())
//				result.add("Valor total: R$" + this.resultset.getString("valorTotal") + "----Valor pago: R$"
//						+ this.resultset.getString("valorPago") + "-----Status: " + this.resultset.getString("status"));
//			int tam = result.size();
			this.statement = this.connection.createStatement();
			String querry = "select * from devedor where idDev ="+id+";";
			this.resultset = this.statement.executeQuery(querry);
			Document document = new Document();
			PdfWriter.getInstance(document,
					new FileOutputStream(""+System.getProperty("user.home")+"\\Desktop\\Devedor--" + id + ".pdf"));
			document.open();
			Image image = Image.getInstance("cabecalho.png");
			image.setAlignment(Element.ALIGN_CENTER);
			document.add(image);
			PdfPTable tabela = new PdfPTable(1);
			PdfPCell cabecalho = new PdfPCell(new Paragraph("Devedor"));
			PdfPCell parcela = new PdfPCell(new Paragraph("Parcelas"));
			PdfPCell divida = new PdfPCell(new Paragraph("Dívida"));
			divida.setHorizontalAlignment(Element.ALIGN_CENTER);
			if (this.resultset.next()) {
				cabecalho.setHorizontalAlignment(Element.ALIGN_CENTER);
				parcela.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabela.addCell(cabecalho);
				tabela.addCell("ID devedor: " + this.resultset.getString("idDev") + "");
				tabela.addCell("Identidade devedor: " + this.resultset.getString("identidadeDev") + "");
				tabela.addCell("Nome devedor: " + this.resultset.getString("nomeDev") + "");
				tabela.addCell("Email devedor: " + this.resultset.getString("emailDev") + "");
				tabela.addCell("Telefone devedor: " + this.resultset.getString("telDev") + "");
				tabela.addCell("Endereco devedor: " + this.resultset.getString("endDev") + "");
			}
			this.statement = this.connection.createStatement();
			String div = "select cliente.*, devedor.*, divida.*, funcionario.nomeFunc from divida \r\n"
					+ "inner join cliente on divida.idCli = cliente.idCli\r\n"
					+ "inner join devedor on divida.idDev = devedor.idDev\r\n"
					+ "inner join funcionario on divida.idFunc = funcionario.idFunc\r\n" + "where cliente.idCli =" + id
					+ ";";
			this.resultset = this.statement.executeQuery(div);
			this.resultset.beforeFirst();
			while (this.resultset.next()) {
				tabela.addCell(divida);
				tabela.addCell("ID dívida: " + this.resultset.getString("iddivida") + "");
				tabela.addCell("Responsável: " + this.resultset.getString("nomeFunc") + "");
				tabela.addCell("Devedor: " + this.resultset.getString("nomeDev") + "");
				tabela.addCell("Tipo da dívida: " + this.resultset.getString("tipoDiv") + "");
				tabela.addCell("Forma de pagamento: " + this.resultset.getString("formaPag") + "");
				tabela.addCell("Valor total: " + this.resultset.getString("valorDiv") + "");
//				if (tam > 0) {
//					tabela.addCell("Número de parcelas: " + tam + "");
//					tabela.addCell(parcela);
//					for (int i = 0; i < result.size(); i++)
//						tabela.addCell("" + result.get(i) + "");
//				}
			}
			document.add(tabela);
			document.close();
		} catch (SQLException | DocumentException | IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public static int getLogado() {
		return logado;
	}

	private static void setLogado(String logado) {
		BancoDeDados.logado = Integer.parseInt(logado);
	}

	public static void setSecDev(String secDev) {
		BancoDeDados.secDev = secDev;
	}

	public static void setSecFunc(String secFunc) {
		BancoDeDados.secFunc = secFunc;
	}

	public static void setSecCli(String secCli) {
		BancoDeDados.secCli = secCli;
	}

	public static void setSecDiv(String secDiv) {
		BancoDeDados.secDiv = secDiv;
	}
}