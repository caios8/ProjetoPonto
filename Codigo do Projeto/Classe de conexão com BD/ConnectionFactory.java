package aula1;

/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public static Connection obtemConexao() throws SQLException {
		String password = "";
		return DriverManager
				.getConnection("jdbc:mysql://localhost/cybersupply?user=root&password="+password+"&useTimezone=true&serverTimezone=America/Sao_Paulo");
	}

}
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Obtém conexão com o banco de dados
	public static Connection obtemConexao() throws SQLException {
		String servidor = "localhost";
		String porta = "3306";
		String database = "Negast";
		return DriverManager
				.getConnection("jdbc:mysql://" + servidor + ":" + porta + "/" + database + "?useTimezone=true&serverTimezone=UTC", "root", "");
	}

}