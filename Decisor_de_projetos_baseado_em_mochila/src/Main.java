import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GerenciadorDeProjetos gerenciador = new GerenciadorDeProjetos();

        // Criação do Projeto 1 com orçamento de 100.0
        Projeto projeto1 = new Projeto(1, "Projeto Alpha", 100.0);
        gerenciador.adicionarProjeto(projeto1);
        gerenciador.adicionarRecursoAoProjeto(1, new Recurso(1, "Recurso A1", 30.0, 40.0));
        gerenciador.adicionarRecursoAoProjeto(1, new Recurso(2, "Recurso A2", 20.0, 30.0));
        gerenciador.adicionarRecursoAoProjeto(1, new Recurso(3, "Recurso A3", 50.0, 60.0));
        gerenciador.adicionarRecursoAoProjeto(1, new Recurso(4, "Recurso A4", 40.0, 70.0));
        gerenciador.adicionarRecursoAoProjeto(1, new Recurso(5, "Recurso A5", 10.0, 15.0));

        // Criação do Projeto 2 com orçamento de 200.0
        Projeto projeto2 = new Projeto(2, "Projeto Beta", 200.0);
        gerenciador.adicionarProjeto(projeto2);
        gerenciador.adicionarRecursoAoProjeto(2, new Recurso(6, "Recurso B1", 60.0, 90.0));
        gerenciador.adicionarRecursoAoProjeto(2, new Recurso(7, "Recurso B2", 80.0, 100.0));
        gerenciador.adicionarRecursoAoProjeto(2, new Recurso(8, "Recurso B3", 30.0, 45.0));
        gerenciador.adicionarRecursoAoProjeto(2, new Recurso(9, "Recurso B4", 50.0, 60.0));
        gerenciador.adicionarRecursoAoProjeto(2, new Recurso(10, "Recurso B5", 40.0, 55.0));

        // Criação do Projeto 3 com orçamento de 150.0
        Projeto projeto3 = new Projeto(3, "Projeto Gamma", 150.0);
        gerenciador.adicionarProjeto(projeto3);
        gerenciador.adicionarRecursoAoProjeto(3, new Recurso(11, "Recurso C1", 70.0, 80.0));
        gerenciador.adicionarRecursoAoProjeto(3, new Recurso(12, "Recurso C2", 20.0, 25.0));
        gerenciador.adicionarRecursoAoProjeto(3, new Recurso(13, "Recurso C3", 40.0, 50.0));
        gerenciador.adicionarRecursoAoProjeto(3, new Recurso(14, "Recurso C4", 30.0, 35.0));
        gerenciador.adicionarRecursoAoProjeto(3, new Recurso(15, "Recurso C5", 90.0, 100.0));

        // Testando o cálculo da mochila para cada projeto
        calcularMochilaParaProjeto(gerenciador, 1);
        calcularMochilaParaProjeto(gerenciador, 2);
        calcularMochilaParaProjeto(gerenciador, 3);
    }

    private static void calcularMochilaParaProjeto(GerenciadorDeProjetos gerenciador, int projetoId) {
        Projeto projeto = gerenciador.buscarProjetoPorId(projetoId);
        if (projeto != null) {
            List<Recurso> recursosSelecionados = resolverMochila(projeto.getRecursos(), projeto.getOrcamento());
            System.out.println("\nRecursos selecionados para o " + projeto.getNome() + " (Orçamento: " + projeto.getOrcamento() + "):");
            for (Recurso recurso : recursosSelecionados) {
                System.out.println("Recurso ID: " + recurso.getId() + ", Nome: " + recurso.getNome() +
                                   ", Valor Agregado: " + recurso.getValorAgregado() + ", Custo: " + recurso.getCusto());
            }
        } else {
            System.out.println("Projeto com ID " + projetoId + " não encontrado.");
        }
    }

    private static List<Recurso> resolverMochila(List<Recurso> recursos, double orcamento) {
        int n = recursos.size();
        int[][] dp = new int[n + 1][(int) orcamento + 1];
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= orcamento; w++) {
                if (recursos.get(i - 1).getCusto() <= w) {
                    dp[i][w] = Math.max((int) recursos.get(i - 1).getValorAgregado() + dp[i - 1][w - (int) recursos.get(i - 1).getCusto()], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        List<Recurso> resultado = new ArrayList<>();
        for (int i = n, w = (int) orcamento; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                resultado.add(recursos.get(i - 1));
                w -= (int) recursos.get(i - 1).getCusto();
            }
        }
        return resultado;
    }
}
