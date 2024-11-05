import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MochilaAlgoritmoEstrela {
    public List<Recurso> otimizarRecursos(List<Recurso> recursos, double limiteCusto) {
        return resolverAEstrela(recursos, limiteCusto);
    }

    private List<Recurso> resolverAEstrela(List<Recurso> recursos, double limiteCusto) {
        // Fila de prioridade para armazenar os estados de forma ordenada pelo valor heurístico
        PriorityQueue<Estado> fila = new PriorityQueue<>((a, b) -> Double.compare(b.getValorEstimado(), a.getValorEstimado()));
        List<Recurso> melhorSolucao = new ArrayList<>();
        double melhorValor = 0;

        // Estado inicial: mochila vazia
        fila.add(new Estado(new ArrayList<>(), 0, 0, calcularValorMaximoEstimado(new ArrayList<>(), recursos)));

        while (!fila.isEmpty()) {
            Estado atual = fila.poll();

            // Se o valor do estado atual é o maior até agora e está dentro do limite de custo, ele se torna a melhor solução
            if (atual.getValorAcumulado() > melhorValor && atual.getCustoAcumulado() <= limiteCusto) {
                melhorSolucao = atual.getItens();
                melhorValor = atual.getValorAcumulado();
            }

            // Expande os estados adicionando cada recurso ainda não incluído
            for (Recurso recurso : recursos) {
                if (!atual.getItens().contains(recurso)) {
                    List<Recurso> novosItens = new ArrayList<>(atual.getItens());
                    novosItens.add(recurso);

                    double novoCusto = atual.getCustoAcumulado() + recurso.getCusto();
                    double novoValor = atual.getValorAcumulado() + recurso.getValorAgregado();

                    // Apenas adiciona o novo estado se ele estiver dentro do limite de custo
                    if (novoCusto <= limiteCusto) {
                        fila.add(new Estado(novosItens, novoCusto, novoValor, calcularValorMaximoEstimado(novosItens, recursos)));
                    }
                }
            }
        }

        return melhorSolucao;
    }

    private double calcularValorMaximoEstimado(List<Recurso> itensAtuais, List<Recurso> recursos) {
        double valorAtual = itensAtuais.stream().mapToDouble(Recurso::getValorAgregado).sum();
        double valorRestante = recursos.stream()
                .filter(recurso -> !itensAtuais.contains(recurso))
                .mapToDouble(Recurso::getValorAgregado)
                .sum();
        return valorAtual + valorRestante;
    }

    private static class Estado {
        private final List<Recurso> itens;
        private final double custoAcumulado;
        private final double valorAcumulado;
        private final double valorEstimado;

        public Estado(List<Recurso> itens, double custoAcumulado, double valorAcumulado, double valorEstimado) {
            this.itens = itens;
            this.custoAcumulado = custoAcumulado;
            this.valorAcumulado = valorAcumulado;
            this.valorEstimado = valorEstimado;
        }

        public List<Recurso> getItens() {
            return itens;
        }

        public double getCustoAcumulado() {
            return custoAcumulado;
        }

        public double getValorAcumulado() {
            return valorAcumulado;
        }

        public double getValorEstimado() {
            return valorEstimado;
        }
    }
}
