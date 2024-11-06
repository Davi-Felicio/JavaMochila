import java.util.List;

public class TesteGerenciadorDeProjetos {

    public static void main(String[] args) {
        // Inicializando os testes
        GerenciadorDeProjetos gerenciador = new GerenciadorDeProjetos();

        // Testando a adição de projetos
        System.out.println("Testando adição de projetos:");
        Projeto projeto1 = new Projeto("Projeto A", 1000.0);
        Projeto projeto2 = new Projeto("Projeto B", 2000.0);

        gerenciador.adicionarProjeto(projeto1);
        gerenciador.adicionarProjeto(projeto2);

        // Verificando se os projetos foram adicionados
        assert projeto1.getId() != 0 : "Projeto A não foi adicionado corretamente!";
        assert projeto2.getId() != 0 : "Projeto B não foi adicionado corretamente!";
        System.out.println("Projetos adicionados com sucesso!\n");

        // Testando a listagem de projetos
        System.out.println("Testando listagem de projetos:");
        List<Projeto> projetos = gerenciador.listarProjetos();
        assert projetos.size() == 2 : "Deveriam haver 2 projetos!";
        System.out.println("Lista de projetos correta!\n");

        // Testando a adição de recursos ao Projeto A
        System.out.println("Testando adição de recursos ao Projeto A:");
        Recurso recurso1 = new Recurso("Recurso 1", 500.0, 1500.0);
        Recurso recurso2 = new Recurso("Recurso 2", 300.0, 800.0);
        Recurso recurso3 = new Recurso("Recurso 3", 700.0, 1000.0);
        Recurso recurso4 = new Recurso("Recurso 4", 200.0, 1200.0);

        // Adicionando múltiplos recursos
        gerenciador.adicionarRecursoAoProjeto(projeto1.getId(), recurso1);
        gerenciador.adicionarRecursoAoProjeto(projeto1.getId(), recurso2);
        gerenciador.adicionarRecursoAoProjeto(projeto1.getId(), recurso3);
        gerenciador.adicionarRecursoAoProjeto(projeto1.getId(), recurso4);

        // Verificando se os recursos foram adicionados corretamente
        Projeto projetoTestado = gerenciador.buscarProjetoPorId(projeto1.getId());
        assert projetoTestado != null : "Projeto não encontrado!";
        assert projetoTestado.getRecursos().size() == 4 : "Deveriam haver 4 recursos no projeto!";
        System.out.println("4 Recursos adicionados com sucesso ao Projeto A!\n");

        // Verificando o total de recursos no projeto
        for (Recurso recurso : projetoTestado.getRecursos()) {
            System.out.println("Recurso ID: " + recurso.getId() + ", Nome: " + recurso.getNome() + ", Custo: " + recurso.getCusto() + ", Valor Agregado: " + recurso.getValorAgregado());
        }
        System.out.println("\n");

        // Testando a remoção de um recurso específico
       // System.out.println("Testando remoção de um recurso do Projeto A:");
      //  gerenciador.removerRecursoDoProjeto(projeto1.getId(), recurso2.getId());

        // Verificando se o recurso foi removido
        projetoTestado = gerenciador.buscarProjetoPorId(projeto1.getId());
        assert projetoTestado != null : "Projeto não encontrado!";
        assert projetoTestado.getRecursos().size() == 3 : "Deveriam haver 3 recursos no projeto após remoção!";
        System.out.println("Recurso removido com sucesso!\n");

        // Testando a remoção de projeto
        System.out.println("Testando remoção de um projeto:");
        int idProjetoRemover = projeto2.getId();
        gerenciador.removerProjeto(idProjetoRemover);

        Projeto projetoRemovido = gerenciador.buscarProjetoPorId(idProjetoRemover);
        assert projetoRemovido == null : "Projeto não foi removido corretamente!";
        System.out.println("Projeto removido com sucesso!\n");

        // Testando a mochila após a adição de recursos
        System.out.println("Testando cálculo da mochila para o Projeto A:");
        MochilaAlgoritmoEstrela mochilaAlgoritmo = new MochilaAlgoritmoEstrela();
        List<Recurso> recursosSelecionados = mochilaAlgoritmo.otimizarRecursos(projeto1.getRecursos(), projeto1.getOrcamento());

        System.out.println("Recursos selecionados para maximizar o valor agregado:");
        for (Recurso recurso : recursosSelecionados) {
            System.out.println("Recurso ID: " + recurso.getId() + ", Nome: " + recurso.getNome() + ", Valor Agregado: " + recurso.getValorAgregado() + ", Custo: " + recurso.getCusto());
        }
        System.out.println("\nTestes completados com sucesso.");
    }
}
