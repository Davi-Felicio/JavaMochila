import java.util.ArrayList;
import java.util.List;

public class Projeto {
    private static int contadorId = 1; // Contador estático para gerar IDs únicos
    private int id;
    private String nome;
    private double orcamento;
    private List<Recurso> recursos;

    public Projeto(String nome, double orcamento) {
        this.id = contadorId++; // Atribui o ID automaticamente e incrementa o contador
        this.nome = nome;
        this.orcamento = orcamento;
        this.recursos = new ArrayList<>();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    public void adicionarRecurso(Recurso recurso) {
        recursos.add(recurso);
    }

    // Remover recurso por id
    public void removerRecurso(int recursoId) {
        recursos.removeIf(recurso -> recurso.getId() == recursoId);
    }

    // Métodos getters e setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getOrcamento() {
        return orcamento;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void editarProjeto(String novoNome, double novoOrcamento) {
        this.nome = novoNome;
        this.orcamento = novoOrcamento;
    }

    // Busca um recurso por ID
public Recurso buscarRecursoPorId(int recursoId) {
    for (Recurso recurso : recursos) {
        if (recurso.getId() == recursoId) {
            return recurso; // Retorna o recurso encontrado
        }
    }
    return null; // Retorna null se o recurso não for encontrado
}


    @Override
    public String toString() {
        return nome;
    }
}
