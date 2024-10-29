import java.util.ArrayList;
import java.util.List;

public class Projeto {
    private int id;
    private String nome;
    private double orcamento;
    private List<Recurso> recursos = new ArrayList<>();

    public Projeto(int id, String nome, double orcamento) {
        this.id = id;
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
