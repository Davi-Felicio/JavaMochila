public class Recurso {
    private static int contadorDeId = 1;
    private int id;
    private String nome;
    private double custo;
    private double valorAgregado;

    public Recurso(String nome, double custo, double valorAgregado) {
        this.id = contadorDeId++; //
        this.nome = nome;
        this.custo = custo;
        this.valorAgregado = valorAgregado;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getCusto() { return custo; }
    public double getValorAgregado() { return valorAgregado; }
}
