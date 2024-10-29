import java.util.List;

public interface ProjetoRecursoCRUD {
    void adicionarProjeto(Projeto projeto);
    Projeto buscarProjetoPorId(int id);
    List<Projeto> listarProjetos();
    void adicionarRecursoAoProjeto(int projetoId, Recurso recurso);
}
