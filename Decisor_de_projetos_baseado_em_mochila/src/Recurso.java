public class Recurso {
    private static int contadorId = 1; // Contador estático para gerar IDs únicos
    private int id;
    private String nome;
    private double custo;
    private double valorAgregado;

    public Recurso(String nome, double custo, double valorAgregado) {
        this.id = contadorId++; // Atribui o ID e incrementa o contador
        this.nome = nome;
        this.custo = custo;
        this.valorAgregado = valorAgregado;
    }

    // Getters e setters
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

      public void editarRecurso(String novoNome, double novoCusto, double novoValorAgregado) {
        this.nome = novoNome;
        this.custo = novoCusto;
        this.valorAgregado = novoValorAgregado;
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

    // Sobrescrever toString para facilitar a exibição
    @Override
    public String toString() {
        return nome;  // Exibir nome ao invés de todo o objeto
    }
}
