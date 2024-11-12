import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String URL = "jdbc:mysql://127.0.0.1/gerenciadorprojetos";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
