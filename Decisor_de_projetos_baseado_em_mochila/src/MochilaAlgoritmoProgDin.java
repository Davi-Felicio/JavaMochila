import java.util.ArrayList;
import java.util.List;

public class MochilaAlgoritmoProgDin {
    public List<Recurso> otimizarRecursos(List<Recurso> recursos, double limiteCusto) {
        int fatorEscala = 100;  // Escala para evitar valores decimais
        int limiteCustoInt = (int) (limiteCusto * fatorEscala);

        int n = recursos.size();
        double[][] dp = new double[n + 1][limiteCustoInt + 1];

        // Preenchendo a tabela dp usando o algoritmo da mochila
        for (int i = 1; i <= n; i++) {
            Recurso recurso = recursos.get(i - 1);
            int custoRecurso = (int) (recurso.getCusto() * fatorEscala); // Escala o custo

            for (int w = 0; w <= limiteCustoInt; w++) {
                if (custoRecurso <= w) {
                    dp[i][w] = Math.max(recurso.getValorAgregado() + dp[i - 1][w - custoRecurso], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Recuperando os recursos selecionados
        List<Recurso> recursosSelecionados = new ArrayList<>();
        int w = limiteCustoInt;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                Recurso recurso = recursos.get(i - 1);
                recursosSelecionados.add(recurso);
                w -= (int) (recurso.getCusto() * fatorEscala);
            }
        }

        return recursosSelecionados;
    }
}
