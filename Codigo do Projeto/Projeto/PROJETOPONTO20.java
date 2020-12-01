
package aula1;

import javax.swing.JOptionPane;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;

public class PROJETOPONTO20 {

	public static void main(String[] args) {

		SimpleDateFormat ponto = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		Date datahora = new Date();
		String saldacao = "", nome = "", empre, mostra;
		int cod;
		empre = JOptionPane
				.showInputDialog(null, "Digite sua empresa", "Registro de Ponto", JOptionPane.QUESTION_MESSAGE).toUpperCase();

		cod = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite seu código", "Registro de Ponto",
				JOptionPane.QUESTION_MESSAGE));

		nome = carregar(empre, cod);

		if (nome == "") {

			JOptionPane.showMessageDialog(null, "Nada foi encontrado no banco");

		} else {

			System.out.println("O código do funcionário: " + cod);
			System.out.println("O nome do funcionário: " + nome);
			System.out.println("A empresa do funcionário: " + empre);

			JOptionPane.showMessageDialog(null, "Olá " + nome);

			String[] entrada = { "Entrada", "Entrada do Almoço", "Saida do Almoço", "Saida" };

			String pontos = (String) JOptionPane.showInputDialog(null, "Selecione o ponto desejado, " + nome,
					"Marque o seu ponto", JOptionPane.QUESTION_MESSAGE, null, entrada, entrada[0]);

			System.out.println("O ponto selecionado foi: " + pontos);

			if (pontos.equals("Entrada")) {

				saldacao = "Tenha um Ótimo Serviço!";

			} else if (pontos.equals("Entrada do Almoço")) {

				saldacao = "Tenha um Ótimo Almoço!";

			} else if (pontos.equals("Saida do Almoço")) {

				saldacao = "Tenha um Ótimo Retorno!";

			} else if (pontos.equals("Saida")) {

				saldacao = "Tenha um Bom Descanso!";

			}

			Object[] options = { "Sim", "Não" };

			Object x = JOptionPane.showInputDialog(null, "Você confirma o ponto?", "Aviso", 3, null, options, "");

			if (x.equals("Sim")) {
				
				JOptionPane.showMessageDialog(null,
						"O ponto foi batido com sucesso  " + ponto.format(datahora) + "\n" + saldacao);
				atualizar(cod, pontos, ponto.format(datahora));
				
				mostra = carregarFinal(cod, "Entrada") + "\n\n";
				mostra += carregarFinal(cod, "Entrada do Almoço") + "\n\n";
				mostra += carregarFinal(cod, "Saida do Almoço") + "\n\n";
				mostra += carregarFinal(cod, "Saida");
				
				JOptionPane.showMessageDialog(null, mostra);

			} else {

				JOptionPane.showMessageDialog(null, "O ponto não foi batido");

			}
			
			
			
		}
	}

	public static String carregar(String empre, int cod) {

		String nome = "";
		boolean retorno = false;
		String sqlSelect = "SELECT Nome FROM Programa WHERE Empre = ? && Codigo = ?";

		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setString(1, empre);
			stm.setInt(2, cod);
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					nome = rs.getString("Nome");
				} else {
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return nome;
	}
	
	public static String carregarFinal(int cod, String tipo) {

		String nome = "";
		String sqlSelect = "SELECT * FROM Programa WHERE Codigo = ? && Tipo = ?";

		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setInt(1, cod);
			stm.setString(2, tipo);
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					nome = "Funcionario: " + rs.getString("Nome") + "\n" + "Empresa: " + rs.getString("Empre") + "\n" + "Ponto: " + rs.getString("Tipo") + "\n" + "Horario: " + rs.getString("Horario");
				} else {
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return nome;
	}
	
	public static void atualizar(int cod, String tipo, String horario) {

		String sqlUpdate = "UPDATE Programa SET Horario=? WHERE Codigo=? && Tipo = ?";

		try (Connection conn = aula1.ConnectionFactory.obtemConexao();
			PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			stm.setString(1, horario);
			stm.setInt(2, cod);
			stm.setString(3, tipo);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
