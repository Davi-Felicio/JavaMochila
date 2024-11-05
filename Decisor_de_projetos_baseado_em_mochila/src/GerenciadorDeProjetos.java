import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeProjetos implements ProjetoRecursoCRUD {
    private List<Projeto> projetos = new ArrayList<>();

    @Override
    public void adicionarProjeto(Projeto projeto) {
        projetos.add(projeto);
    }

    @Override
    public Projeto buscarProjetoPorId(int id) {
        for (Projeto projeto : projetos) {
            if (projeto.getId() == id) return projeto;
        }
        return null;
    }

    @Override
    public List<Projeto> listarProjetos() {
        return projetos;
    }

    @Override
    public void adicionarRecursoAoProjeto(int projetoId, Recurso recurso) {
        Projeto projeto = buscarProjetoPorId(projetoId);
        if (projeto != null) {
            projeto.adicionarRecurso(recurso);
        } else {
            System.out.println("Projeto não encontrado.");
        }
    }

    public void removerProjeto(int id, Projeto projeto){
        System.out.println("falta implementar kkkkkkk");
    }
}
