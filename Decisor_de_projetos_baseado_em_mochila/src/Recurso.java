import java.sql.*;

public class Recurso {

    private int id;
    private String nome;
    private double custo;
    private double valorAgregado;

    public Recurso(String nome, double custo, double valorAgregado) {

        this.nome = nome;
        this.custo = custo;
        this.valorAgregado = valorAgregado;
    }

    // Método para salvar o recurso no banco de dados


    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getCusto() {
        return custo;
    }

    public double getValorAgregado() {
        return valorAgregado;
    }

    @Override
    public String toString() {
        return nome;
    }
      // Método para editar o recurso
    public void editarRecurso(String novoNome, double novoCusto, double novoValor) {
        this.nome = novoNome;
        this.custo = novoCusto;
        this.valorAgregado = novoValor;
    }

    public Recurso(int id, String nome, double custo, double valorAgregado) {
        this.id = id;
        this.nome = nome;
        this.custo = custo;
        this.valorAgregado = valorAgregado;
    }

    public Recurso() {
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public void setValorAgregado(double valorAgregado) {
        this.valorAgregado = valorAgregado;
    }

    public void setId(int id) {
        this.id = id;
    }
}
