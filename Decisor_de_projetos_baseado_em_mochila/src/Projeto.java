import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Projeto {
    private int id;
    private String nome;
    private double orcamento;
    private List<Recurso> recursos;

    public Projeto(String nome, double orcamento) {

        this.nome = nome;
        this.orcamento = orcamento;
        this.recursos = new ArrayList<>(); // Inicializando a lista de recursos
    }

    public Projeto() {
        this.recursos = new ArrayList<>(); // Inicializando a lista de recursos
    }

    // Método para salvar o projeto no banco de dados
    public void salvar() {
        Connection con = ConexaoBanco.conectar();
        String sql = "INSERT INTO projetos (nome, orcamento) VALUES (?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.nome);
            stmt.setDouble(2, this.orcamento);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1); // Obtém o ID gerado para o projeto
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar o projeto no banco de dados.", e);
        }
    }

    // Método para adicionar recurso ao projeto
    public void adicionarRecurso(Recurso recurso) {
        recursos.add(recurso);
    }

    // Getter para recursos
    public List<Recurso> getRecursos() {
        return recursos;
    }

    // Método para carregar os recursos de um projeto do banco de dados
    public void carregarRecursos() {
        Connection con = ConexaoBanco.conectar();
        String sql = "SELECT id, nome, custo, valor_agregado FROM recursos WHERE id_projeto = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, this.id); // Define o ID do projeto
            ResultSet rs = stmt.executeQuery();

            // Limpa a lista de recursos antes de adicionar os novos
            this.recursos.clear();

            while (rs.next()) {
                int idRecurso = rs.getInt("id");
                String nome = rs.getString("nome");
                double custo = rs.getDouble("custo");
                double valorAgregado = rs.getDouble("valor_agregado");
                Recurso recurso = new Recurso(idRecurso, nome, custo, valorAgregado);
                this.recursos.add(recurso);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar os recursos do projeto do banco de dados.", e);
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nome;
    }

    // Método para editar o projeto
    public void editarProjeto(String novoNome, double novoOrcamento) {
        this.nome = novoNome;
        this.orcamento = novoOrcamento;
    }

    // Método para remover um recurso
    public void removerRecurso(int idRecurso) {
        recursos.removeIf(recurso -> recurso.getId() == idRecurso);
    }
}
