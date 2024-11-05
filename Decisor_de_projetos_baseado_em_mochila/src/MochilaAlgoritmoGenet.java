import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MochilaAlgoritmoGenet {
    private static final int TAMANHO_POPULACAO = 50;
    private static final int NUMERO_GERACOES = 100;
    private static final double TAXA_MUTACAO = 0.1;
    private static final double TAXA_CRUZAMENTO = 0.8;

    public List<Recurso> otimizarRecursos(List<Recurso> recursos, double limiteCusto) {
        List<List<Recurso>> populacao = inicializarPopulacao(recursos);

        for (int geracao = 0; geracao < NUMERO_GERACOES; geracao++) {
            List<List<Recurso>> novaPopulacao = new ArrayList<>();

            // Seleção de indivíduos da população atual
            for (int i = 0; i < TAMANHO_POPULACAO; i++) {
                List<Recurso> pai1 = selecionarIndividuo(populacao, limiteCusto);
                List<Recurso> pai2 = selecionarIndividuo(populacao, limiteCusto);

                // Cruzamento
                List<Recurso> filho = cruzar(pai1, pai2);
                if (calcularCusto(filho) <= limiteCusto) {
                    novaPopulacao.add(filho);
                }

                // Mutação
                if (Math.random() < TAXA_MUTACAO) {
                    mutar(filho, recursos, limiteCusto);
                }
            }

            // Atualizar população
            populacao = novaPopulacao;
        }

        // Encontrar o melhor indivíduo após todas as gerações
        return Collections.max(populacao, (a, b) -> Double.compare(calcularFitness(b), calcularFitness(a)));
    }

    private List<List<Recurso>> inicializarPopulacao(List<Recurso> recursos) {
        List<List<Recurso>> populacao = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < TAMANHO_POPULACAO; i++) {
            List<Recurso> individuo = new ArrayList<>();
            for (Recurso recurso : recursos) {
                if (random.nextBoolean()) {
                    individuo.add(recurso);
                }
            }
            populacao.add(individuo);
        }
        return populacao;
    }

    private List<Recurso> selecionarIndividuo(List<List<Recurso>> populacao, double limiteCusto) {
        Random random = new Random();
        List<Recurso> individuo;
        do {
            individuo = populacao.get(random.nextInt(populacao.size()));
        } while (calcularCusto(individuo) > limiteCusto);
        return individuo;
    }

    private List<Recurso> cruzar(List<Recurso> pai1, List<Recurso> pai2) {
        List<Recurso> filho = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < pai1.size(); i++) {
            filho.add(random.nextBoolean() ? pai1.get(i) : pai2.get(i));
        }
        return filho;
    }

    private void mutar(List<Recurso> individuo, List<Recurso> recursos, double limiteCusto) {
        Random random = new Random();
        int index = random.nextInt(recursos.size());
        Recurso recurso = recursos.get(index);

        if (calcularCusto(individuo) + recurso.getCusto() <= limiteCusto) {
            if (!individuo.contains(recurso)) {
                individuo.add(recurso);
            } else {
                individuo.remove(recurso);
            }
        }
    }

    private double calcularFitness(List<Recurso> individuo) {
        return individuo.stream().mapToDouble(Recurso::getValorAgregado).sum();
    }

    private double calcularCusto(List<Recurso> individuo) {
        return individuo.stream().mapToDouble(Recurso::getCusto).sum();
    }
}
