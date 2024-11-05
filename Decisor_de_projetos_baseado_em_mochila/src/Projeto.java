import java.util.ArrayList;
import java.util.List;

public class Projeto {
    private static int contadorDeId = 1; // Contador estático para gerar IDs únicos
    private int id;
    private String nome;
    private double orcamento;
    private List<Recurso> recursos = new ArrayList<>();

    public Projeto(String nome, double orcamento) {
        this.id = contadorDeId++; // Atribui o valor do contador e incrementa
        this.nome = nome;
        this.orcamento = orcamento;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getOrcamento() { return orcamento; }

    public void adicionarRecurso(Recurso recurso) {
        recursos.add(recurso);
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }
}
