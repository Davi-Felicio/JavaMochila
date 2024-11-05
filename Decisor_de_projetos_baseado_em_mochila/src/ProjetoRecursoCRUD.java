import java.util.List;

public interface ProjetoRecursoCRUD {
    void adicionarProjeto(Projeto projeto);
    Projeto buscarProjetoPorId(int id);
    List<Projeto> listarProjetos();
    void atualizarProjeto(int id, Projeto projetoatualizado);
    void removerProjeto(int id);
    void adicionarRecursoAoProjeto(int projetoId, Recurso recurso);
}

