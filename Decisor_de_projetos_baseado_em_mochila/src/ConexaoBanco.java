import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static Connection conexao = null;

    // Método para obter a conexão persistente
    public static Connection conectar() {
        if (conexao == null) {
            try {
                conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/gerenciadorprojetos", "root", "root");
                System.out.println("Conexão estabelecida com sucesso.");
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao conectar com o banco de dados.", e);
            }
        }
        return conexao;
    }

    // Método para desconectar a conexão
    public static void desconectar() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
                System.out.println("Conexão fechada com sucesso.");
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar a conexão com o banco de dados.", e);
            }
        }
    }
}
