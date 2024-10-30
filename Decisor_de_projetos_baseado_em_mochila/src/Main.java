import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


//essa main é temporária e só serve para testar as classes, posteriormente uma interface gráfica sera criada


public class Main {
    private static GerenciadorDeProjetos gerenciador = new GerenciadorDeProjetos();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\nSelecione uma opção:");
                System.out.println("1. Adicionar Projeto");
                System.out.println("2. Adicionar Recurso a um Projeto");
                System.out.println("3. Listar Projetos");
                System.out.println("4. Atualizar Projeto");
                System.out.println("5. Remover Projeto");
                System.out.println("6. Calcular Mochila para um Projeto");
                System.out.println("0. Sair");
                System.out.print("Escolha: ");
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1 -> adicionarProjeto();
                    case 2 -> adicionarRecurso();
                    case 3 -> listarProjetos();
                    case 4 -> atualizarProjeto();
                    case 5 -> removerProjeto();
                    case 6 -> calcularMochila();
                    case 0 -> System.exit(0);
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                scanner.nextLine(); // Limpa o buffer do scanner
            }
        }
    }

    private static void adicionarProjeto() {
        try {
            System.out.print("ID do Projeto: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Nome do Projeto: ");
            String nome = scanner.nextLine();
            System.out.print("Orçamento do Projeto: ");
            double orcamento = scanner.nextDouble();

            Projeto projeto = new Projeto(id, nome, orcamento);
            gerenciador.adicionarProjeto(projeto);
            System.out.println("Projeto adicionado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao adicionar projeto: " + e.getMessage());
            scanner.nextLine(); // Limpa o buffer do scanner
        }
    }

    private static void adicionarRecurso() {
        try {
            System.out.print("ID do Projeto: ");
            int projetoId = scanner.nextInt();
            System.out.print("ID do Recurso: ");
            int recursoId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Nome do Recurso: ");
            String nomeRecurso = scanner.nextLine();
            System.out.print("Custo do Recurso: ");
            double custo = scanner.nextDouble();
            System.out.print("Valor Agregado do Recurso: ");
            double valorAgregado = scanner.nextDouble();

            Recurso recurso = new Recurso(recursoId, nomeRecurso, custo, valorAgregado);
            gerenciador.adicionarRecursoAoProjeto(projetoId, recurso);
            System.out.println("Recurso adicionado ao projeto.");
        } catch (Exception e) {
            System.out.println("Erro ao adicionar recurso: " + e.getMessage());
            scanner.nextLine(); // Limpa o buffer do scanner
        }
    }

    private static void listarProjetos() {
        try {
            List<Projeto> projetos = gerenciador.listarProjetos();
            for (Projeto projeto : projetos) {
                System.out.println("Projeto ID: " + projeto.getId() + ", Nome: " + projeto.getNome() + ", Orçamento: " + projeto.getOrcamento());
                for (Recurso recurso : projeto.getRecursos()) {
                    System.out.println("  Recurso ID: " + recurso.getId() + ", Nome: " + recurso.getNome() + ", Custo: " + recurso.getCusto() + ", Valor Agregado: " + recurso.getValorAgregado());
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar projetos: " + e.getMessage());
        }
    }

    private static void atualizarProjeto() {
        try {
            System.out.print("ID do Projeto a atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Projeto projeto = gerenciador.buscarProjetoPorId(id);
            if (projeto != null) {
                System.out.print("Novo Nome do Projeto: ");
                String novoNome = scanner.nextLine();
                System.out.print("Novo Orçamento do Projeto: ");
                double novoOrcamento = scanner.nextDouble();

                projeto.setNome(novoNome);
                projeto.setOrcamento(novoOrcamento);
                System.out.println("Projeto atualizado com sucesso.");
            } else {
                System.out.println("Projeto não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar projeto: " + e.getMessage());
            scanner.nextLine(); // Limpa o buffer do scanner
        }
    }

    private static void removerProjeto() {
        try {
            System.out.print("ID do Projeto a remover: ");
            int id = scanner.nextInt();
            Projeto projeto = gerenciador.buscarProjetoPorId(id);
            if (projeto != null) {
                gerenciador.removerProjeto(id, projeto);//FALTA IMPLEMENTAR ESSA PARTE NA CLASSE AAAAAAAAAAAAAAA
                System.out.println("Projeto removido com sucesso.");
            } else {
                System.out.println("Projeto não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao remover projeto: " + e.getMessage());
            scanner.nextLine(); // Limpa o buffer do scanner
        }
    }

    private static void calcularMochila() {
        try {
            System.out.print("ID do Projeto para calcular a mochila: ");
            int id = scanner.nextInt();
            Projeto projeto = gerenciador.buscarProjetoPorId(id);
            if (projeto != null) {
                List<Recurso> recursosSelecionados = resolverMochila(projeto.getRecursos(), projeto.getOrcamento());
                System.out.println("Recursos selecionados para maximizar o valor agregado:");
                for (Recurso recurso : recursosSelecionados) {
                    System.out.println("Recurso ID: " + recurso.getId() + ", Nome: " + recurso.getNome() + ", Valor Agregado: " + recurso.getValorAgregado()+ ",  Custo:  "+ recurso.getCusto());
                }
            } else {
                System.out.println("Projeto não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao calcular a mochila: " + e.getMessage());
            scanner.nextLine(); // Limpa o buffer do scanner
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