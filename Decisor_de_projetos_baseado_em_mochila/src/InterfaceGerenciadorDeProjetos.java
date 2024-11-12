import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class InterfaceGerenciadorDeProjetos extends JFrame {
    private JList<Projeto> listaProjetos;
    private DefaultListModel<Projeto> modeloProjetos;
    private JList<Recurso> listaRecursos;
    private DefaultListModel<Recurso> modeloRecursos;
    private GerenciadorDeProjetos gerenciador;

    public InterfaceGerenciadorDeProjetos() {
        gerenciador = new GerenciadorDeProjetos();
        modeloProjetos = new DefaultListModel<>();
        modeloRecursos = new DefaultListModel<>();
        listaProjetos = new JList<>(modeloProjetos);
        listaRecursos = new JList<>(modeloRecursos);

        // Customização para exibir o nome do projeto ao lado do recurso
        listaRecursos.setCellRenderer(new ListCellRenderer<Recurso>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Recurso> list, Recurso value, int index, boolean isSelected, boolean cellHasFocus) {
                String texto = value.getNome() + " (Projeto: " + getProjetoDoRecurso(value).getNome() + ")";
                JLabel label = new JLabel(texto);
                if (isSelected) {
                    label.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                    label.setOpaque(true);
                }
                return label;
            }
        });

        setTitle("Gerenciador de Projetos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de listagem de projetos
        JPanel painelProjetos = new JPanel(new BorderLayout());
        JScrollPane scrollProjetos = new JScrollPane(listaProjetos);
        painelProjetos.add(scrollProjetos, BorderLayout.CENTER);

        // Botões para manipulação de projetos
        JPanel painelBotoesProjetos = new JPanel(new FlowLayout());
        JButton adicionarProjetoButton = new JButton("Adicionar Projeto");
        adicionarProjetoButton.addActionListener(e -> adicionarProjeto());
        JButton removerProjetoButton = new JButton("Remover Projeto");
        removerProjetoButton.addActionListener(e -> removerProjeto());
        JButton editarProjetoButton = new JButton("Editar Projeto");
        editarProjetoButton.addActionListener(e -> editarProjeto());

        painelBotoesProjetos.add(adicionarProjetoButton);
        painelBotoesProjetos.add(removerProjetoButton);
        painelBotoesProjetos.add(editarProjetoButton);
        painelProjetos.add(painelBotoesProjetos, BorderLayout.SOUTH);

        // Painel de recursos e cálculo da mochila
        JPanel painelRecursos = new JPanel(new BorderLayout());
        JScrollPane scrollRecursos = new JScrollPane(listaRecursos);
        painelRecursos.add(scrollRecursos, BorderLayout.CENTER);

        // Botões para manipulação de recursos
        JPanel painelBotoesRecursos = new JPanel(new FlowLayout());
        JButton adicionarRecursoButton = new JButton("Adicionar Recurso");
        adicionarRecursoButton.addActionListener(e -> adicionarRecurso());
        JButton editarRecursoButton = new JButton("Editar Recurso");
        editarRecursoButton.addActionListener(e -> editarRecurso());
        JButton removerRecursoButton = new JButton("Remover Recurso");
        removerRecursoButton.addActionListener(e -> removerRecurso());

        painelBotoesRecursos.add(adicionarRecursoButton);
        painelBotoesRecursos.add(editarRecursoButton);
        painelBotoesRecursos.add(removerRecursoButton);
        painelRecursos.add(painelBotoesRecursos, BorderLayout.SOUTH);

        // Adicionar painéis à tela
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelProjetos, painelRecursos);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // Painel de calcular mochila
        JPanel painelDetalhesProjeto = new JPanel(new BorderLayout());
        JButton calcularMochilaButton = new JButton("Calcular Mochila");
        calcularMochilaButton.addActionListener(e -> calcularMochila());
        painelDetalhesProjeto.add(calcularMochilaButton, BorderLayout.NORTH);
        add(painelDetalhesProjeto, BorderLayout.SOUTH);

        // Listener para carregar recursos do projeto selecionado
        listaProjetos.addListSelectionListener(e -> carregarRecursosDoProjeto(listaProjetos.getSelectedValue()));
    }

    private void adicionarProjeto() {
        String nomeProjeto = JOptionPane.showInputDialog("Digite o nome do projeto:");
        if (nomeProjeto != null && !nomeProjeto.trim().isEmpty()) {
            double orcamento = Double.parseDouble(JOptionPane.showInputDialog("Digite o orçamento do projeto:"));
            Projeto novoProjeto = new Projeto(nomeProjeto, orcamento);
            gerenciador.adicionarProjeto(novoProjeto);
            modeloProjetos.addElement(novoProjeto);
        }
    }

    private void removerProjeto() {
        Projeto projetoSelecionado = listaProjetos.getSelectedValue();
        if (projetoSelecionado != null) {
            gerenciador.removerProjeto(projetoSelecionado.getId());
            modeloProjetos.removeElement(projetoSelecionado);
            carregarRecursosDoProjeto(null); // Limpa a lista de recursos ao remover o projeto
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para remover.");
        }
    }

    private void editarProjeto() {
        Projeto projetoSelecionado = listaProjetos.getSelectedValue();
        if (projetoSelecionado != null) {
            String novoNome = JOptionPane.showInputDialog("Novo nome do projeto:", projetoSelecionado.getNome());
            double novoOrcamento = Double.parseDouble(JOptionPane.showInputDialog("Novo orçamento do projeto:", projetoSelecionado.getOrcamento()));
            projetoSelecionado.editarProjeto(novoNome, novoOrcamento);
            modeloProjetos.setElementAt(projetoSelecionado, listaProjetos.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para editar.");
        }
    }

    private void adicionarRecurso() {
        Projeto projetoSelecionado = listaProjetos.getSelectedValue();
        if (projetoSelecionado != null) {
            String nomeRecurso = JOptionPane.showInputDialog("Digite o nome do recurso:");
            double custo = Double.parseDouble(JOptionPane.showInputDialog("Digite o custo do recurso:"));
            double valorAgregado = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor agregado do recurso:"));
            Recurso novoRecurso = new Recurso(nomeRecurso, custo, valorAgregado);
            projetoSelecionado.adicionarRecurso(novoRecurso);
            modeloRecursos.addElement(novoRecurso);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para adicionar recursos.");
        }
    }

    private void editarRecurso() {
        Recurso recursoSelecionado = listaRecursos.getSelectedValue();
        if (recursoSelecionado != null) {
            String novoNome = JOptionPane.showInputDialog("Novo nome do recurso:", recursoSelecionado.getNome());
            double novoCusto = Double.parseDouble(JOptionPane.showInputDialog("Novo custo do recurso:", recursoSelecionado.getCusto()));
            double novoValor = Double.parseDouble(JOptionPane.showInputDialog("Novo valor agregado do recurso:", recursoSelecionado.getValorAgregado()));
            recursoSelecionado.editarRecurso(novoNome, novoCusto, novoValor);
            modeloRecursos.setElementAt(recursoSelecionado, listaRecursos.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um recurso para editar.");
        }
    }

    private void removerRecurso() {
        Recurso recursoSelecionado = listaRecursos.getSelectedValue();
        Projeto projetoSelecionado = listaProjetos.getSelectedValue();
        if (projetoSelecionado != null && recursoSelecionado != null) {
            projetoSelecionado.removerRecurso(recursoSelecionado.getId());
            modeloRecursos.removeElement(recursoSelecionado);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um recurso para remover.");
        }
    }

    private void calcularMochila() {
        Projeto projetoSelecionado = listaProjetos.getSelectedValue();
        if (projetoSelecionado != null) {
            List<Recurso> recursos = projetoSelecionado.getRecursos();
            double orcamento = projetoSelecionado.getOrcamento();
            MochilaAlgoritmoEstrela algoritmo = new MochilaAlgoritmoEstrela();
            List<Recurso> recursosOtimizados = algoritmo.otimizarRecursos(recursos, orcamento);

            StringBuilder resultado = new StringBuilder("Recursos Otimizados para o Projeto: " + projetoSelecionado.getNome() + "\n");
            resultado.append("Orçamento do Projeto: ").append(orcamento).append("\n\n");
            for (Recurso recurso : recursosOtimizados) {
                resultado.append("Recurso: ").append(recurso.getNome())
                        .append(", Custo: ").append(recurso.getCusto())
                        .append(", Valor Agregado: ").append(recurso.getValorAgregado()).append("\n");
            }
            JOptionPane.showMessageDialog(this, resultado.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para calcular a mochila.");
        }
    }

    private void carregarRecursosDoProjeto(Projeto projeto) {
        modeloRecursos.clear();
        if (projeto != null) {
            for (Recurso recurso : projeto.getRecursos()) {
                modeloRecursos.addElement(recurso);
            }
        }
    }

    private Projeto getProjetoDoRecurso(Recurso recurso) {
        for (int i = 0; i < modeloProjetos.size(); i++) {
            Projeto projeto = modeloProjetos.get(i);
            if (projeto.getRecursos().contains(recurso)) {
                return projeto;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ConexaoBanco banco = new ConexaoBanco();
        try {
            banco.conectar();
        }
         catch (SQLException e) {
          throw new RuntimeException(e);}


        SwingUtilities.invokeLater(() -> {
            InterfaceGerenciadorDeProjetos frame = new InterfaceGerenciadorDeProjetos();
            frame.setVisible(true);
        });
    }
}
