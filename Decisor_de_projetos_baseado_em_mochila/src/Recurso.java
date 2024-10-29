public class Recurso {
    private int id;
    private String nome;
    private double custo;
    private double valorAgregado;

    public Recurso(int id, String nome, double custo, double valorAgregado) {
        this.id = id;
        this.nome = nome;
        this.custo = custo;
        this.valorAgregado = valorAgregado;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getCusto() { return custo; }
    public double getValorAgregado() { return valorAgregado; }
}
