import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeProjetos {

    private Connection conn;

    public GerenciadorDeProjetos() {
        this.conn = ConexaoBanco.conectar(); // Assume que ConexaoBanco é a classe responsável pela conexão
    }

    // Método para adicionar um projeto ao banco de dados
    public void adicionarProjeto(Projeto projeto) throws SQLException {
    String sql = "INSERT INTO projetos (nome, orcamento) VALUES (?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, projeto.getNome());
        stmt.setDouble(2, projeto.getOrcamento());
        stmt.executeUpdate();

        // Recupera a chave gerada
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int idGerado = generatedKeys.getInt(1); // Obtém o ID gerado
                projeto.setId(idGerado);                // Define o ID no objeto
            } else {
                throw new SQLException("Falha ao obter o ID do projeto.");
            }
        }
    }
}


    // Método para editar um projeto no banco de dados
    public void editarProjeto(Projeto projeto) throws SQLException {
        String sql = "UPDATE projetos SET nome = ?, orcamento = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, projeto.getNome());
            stmt.setDouble(2, projeto.getOrcamento());
            stmt.setInt(3, projeto.getId());
            stmt.executeUpdate();
        }
    }

    // Método para remover um projeto do banco de dados
    public void removerProjeto(int idProjeto) throws SQLException {
        String sql = "DELETE FROM projetos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            stmt.executeUpdate();
        }
    }

    // Método para adicionar um recurso ao banco de dados
    public void adicionarRecurso(Recurso recurso, int idProjeto) throws SQLException {
        String sql = "INSERT INTO recursos (nome, custo, valor_agregado, id_projeto) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, recurso.getNome());
            stmt.setDouble(2, recurso.getCusto());
            stmt.setDouble(3, recurso.getValorAgregado());
            stmt.setInt(4, idProjeto);
            stmt.executeUpdate();
        }
    }

    // Método para editar um recurso no banco de dados
    public void editarRecurso(Recurso recurso) throws SQLException {
        String sql = "UPDATE recursos SET nome = ?, custo = ?, valor_agregado = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, recurso.getNome());
            stmt.setDouble(2, recurso.getCusto());
            stmt.setDouble(3, recurso.getValorAgregado());
            stmt.setInt(4, recurso.getId());
            stmt.executeUpdate();
        }
    }

    // Método para remover um recurso do banco de dados
    public void removerRecurso(int idRecurso) throws SQLException {
        String sql = "DELETE FROM recursos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRecurso);
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os projetos do banco de dados
    public List<Projeto> listarProjetos() throws SQLException {
        List<Projeto> projetos = new ArrayList<>();
        String sql = "SELECT * FROM projetos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setOrcamento(rs.getDouble("orcamento"));
                projetos.add(projeto);
            }
        }
        return projetos;
    }

    // Método para listar os recursos de um projeto específico
    public List<Recurso> listarRecursos(int idProjeto) throws SQLException {
        List<Recurso> recursos = new ArrayList<>();
        String sql = "SELECT * FROM recursos WHERE id_projeto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recurso recurso = new Recurso();
                    recurso.setId(rs.getInt("id"));
                    recurso.setNome(rs.getString("nome"));
                    recurso.setCusto(rs.getDouble("custo"));
                    recurso.setValorAgregado(rs.getDouble("valor_agregado"));
                    recursos.add(recurso);
                }
            }
        }
        return recursos;
    }
}
