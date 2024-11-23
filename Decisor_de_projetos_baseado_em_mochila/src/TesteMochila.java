import java.util.List;

public class TesteMochila {
    public static void main(String[] args) {
        // Teste 1: Orçamento padrão
        System.out.println("Teste 1: Orçamento padrão (90)");
        realizarTeste(90);

        // Teste 2: Orçamento maior que o necessário (teste de todos os recursos dentro do orçamento)
        System.out.println("\nTeste 2: Orçamento maior que o necessário (200)");
        realizarTeste(200);

        // Teste 3: Orçamento insuficiente (teste quando o orçamento é menor que o custo de todos os recursos)
        System.out.println("\nTeste 3: Orçamento muito baixo (20)");
        realizarTeste(20);

        // Teste 4: Orçamento muito alto
        System.out.println("\nTeste 4: Orçamento muito alto (1000)");
        realizarTeste(1000);

        // Teste 5: Recursos com valores e custos muito diferentes
        System.out.println("\nTeste 5: Recursos com valores e custos muito diferentes");
        realizarTesteComRecursosDiversos();
    }

    private static void realizarTeste(double limiteCusto) {
        // Criando vários recursos
        Recurso recurso1 = new Recurso("Recurso 1", 50, 100);  // Alto valor, custo médio
        Recurso recurso2 = new Recurso("Recurso 2", 30, 80);   // Médio valor, baixo custo
        Recurso recurso3 = new Recurso("Recurso 3", 20, 60);   // Baixo valor, baixo custo
        Recurso recurso4 = new Recurso("Recurso 4", 10, 30);   // Baixo valor, baixo custo
        Recurso recurso5 = new Recurso("Recurso 5", 60, 150);  // Alto custo, muito alto valor
        Recurso recurso6 = new Recurso("Recurso 6", 40, 90);   // Médio custo, médio valor
        Recurso recurso7 = new Recurso("Recurso 7", 15, 25);   // Muito baixo custo, valor baixo
        Recurso recurso8 = new Recurso("Recurso 8", 70, 120);  // Alto custo, alto valor
        Recurso recurso9 = new Recurso("Recurso 9", 100, 200); // Muito alto custo e valor
        Recurso recurso10 = new Recurso("Recurso 10", 25, 70); // Custo médio, valor médio

        // Criando o projeto e adicionando recursos
        Projeto projeto = new Projeto("Projeto Teste", limiteCusto);  // Orçamento do projeto
        projeto.adicionarRecurso(recurso1);
        projeto.adicionarRecurso(recurso2);
        projeto.adicionarRecurso(recurso3);
        projeto.adicionarRecurso(recurso4);
        projeto.adicionarRecurso(recurso5);
        projeto.adicionarRecurso(recurso6);
        projeto.adicionarRecurso(recurso7);
        projeto.adicionarRecurso(recurso8);
        projeto.adicionarRecurso(recurso9);
        projeto.adicionarRecurso(recurso10);

        // Testando a otimização usando o algoritmo A*
        MochilaAlgoritmoEstrela mochilaAlgoritmo = new MochilaAlgoritmoEstrela();
        List<Recurso> recursosOtimizados = mochilaAlgoritmo.otimizarRecursos(projeto.getRecursos(), limiteCusto);

        // Exibindo os resultados
        System.out.println("Recursos otimizados para o projeto: " + projeto.getNome());
        double custoTotal = 0;
        double valorTotal = 0;
        System.out.println("\033[34mOrçamento disponível: " + projeto.getOrcamento() + "\033[0m");
        for (Recurso r : recursosOtimizados) {
            System.out.println("Recurso: " + r.getNome() + " - Custo: \033[31m" + r.getCusto() + "\033[0m - Valor: \033[32m" + r.getValorAgregado() + "\033[0m");
            custoTotal += r.getCusto();
            valorTotal += r.getValorAgregado();
        }
        System.out.println("Custo total: \033[31m" + custoTotal + "\033[0m");
        System.out.println("Valor total: \033[32m" + valorTotal + "\033[0m");
    }

    private static void realizarTesteComRecursosDiversos() {
        // Criando recursos com valores e custos bem variados
        Recurso recurso1 = new Recurso("Recurso 1", 50, 200);  // Alto valor, alto custo
        Recurso recurso2 = new Recurso("Recurso 2", 10, 50);   // Baixo valor, baixo custo
        Recurso recurso3 = new Recurso("Recurso 3", 80, 100);  // Alto custo, médio valor
        Recurso recurso4 = new Recurso("Recurso 4", 25, 20);   // Médio custo, baixo valor
        Recurso recurso5 = new Recurso("Recurso 5", 5, 10);    // Baixo custo, baixo valor
        Recurso recurso6 = new Recurso("Recurso 6", 60, 180);  // Alto custo, muito alto valor
        Recurso recurso7 = new Recurso("Recurso 7", 40, 90);   // Custo médio, valor médio
        Recurso recurso8 = new Recurso("Recurso 8", 30, 75);   // Custo médio, valor bom
        Recurso recurso9 = new Recurso("Recurso 9", 100, 250); // Muito alto custo, muito alto valor
        Recurso recurso10 = new Recurso("Recurso 10", 15, 40); // Baixo custo, valor razoável

        // Criando o projeto e adicionando recursos
        Projeto projeto = new Projeto("Projeto Teste Diverso", 80);  // Orçamento do projeto
        projeto.adicionarRecurso(recurso1);
        projeto.adicionarRecurso(recurso2);
        projeto.adicionarRecurso(recurso3);
        projeto.adicionarRecurso(recurso4);
        projeto.adicionarRecurso(recurso5);
        projeto.adicionarRecurso(recurso6);
        projeto.adicionarRecurso(recurso7);
        projeto.adicionarRecurso(recurso8);
        projeto.adicionarRecurso(recurso9);
        projeto.adicionarRecurso(recurso10);

        // Testando a otimização com orçamento de 80
        System.out.println("Orçamento de 80:");
        MochilaAlgoritmoEstrela mochilaAlgoritmo = new MochilaAlgoritmoEstrela();
        List<Recurso> recursosOtimizados = mochilaAlgoritmo.otimizarRecursos(projeto.getRecursos(), 80);

        // Exibindo os resultados
        System.out.println("Recursos otimizados para o projeto: " + projeto.getNome());
        double custoTotal = 0;
        double valorTotal = 0;
        System.out.println("\033[34mOrçamento disponível: " + projeto.getOrcamento() + "\033[0m");
        for (Recurso r : recursosOtimizados) {
            System.out.println("Recurso: " + r.getNome() + " - Custo: \033[31m" + r.getCusto() + "\033[0m - Valor: \033[32m" + r.getValorAgregado() + "\033[0m");
            custoTotal += r.getCusto();
            valorTotal += r.getValorAgregado();
        }
        System.out.println("Custo total: \033[31m" + custoTotal + "\033[0m");
        System.out.println("Valor total: \033[32m" + valorTotal + "\033[0m");
    }
}
