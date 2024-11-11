import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        setTitle("Gerenciador de Projetos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de listagem de projetos
        JPanel painelProjetos = new JPanel();
        painelProjetos.setLayout(new BorderLayout());

        JScrollPane scrollProjetos = new JScrollPane(listaProjetos);
        painelProjetos.add(scrollProjetos, BorderLayout.CENTER);

        // Botões para manipulação de projetos
        JPanel painelBotoesProjetos = new JPanel();
        painelBotoesProjetos.setLayout(new FlowLayout());

        JButton adicionarProjetoButton = new JButton("Adicionar Projeto");
        adicionarProjetoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProjeto();
            }
        });

        JButton removerProjetoButton = new JButton("Remover Projeto");
        removerProjetoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProjeto();
            }
        });

        painelBotoesProjetos.add(adicionarProjetoButton);
        painelBotoesProjetos.add(removerProjetoButton);

        painelProjetos.add(painelBotoesProjetos, BorderLayout.SOUTH);

        // Painel de recursos e cálculo da mochila
        JPanel painelRecursos = new JPanel();
        painelRecursos.setLayout(new BorderLayout());

        JScrollPane scrollRecursos = new JScrollPane(listaRecursos);
        painelRecursos.add(scrollRecursos, BorderLayout.CENTER);

        // Botões para manipulação de recursos
        JPanel painelBotoesRecursos = new JPanel();
        painelBotoesRecursos.setLayout(new FlowLayout());

        JButton adicionarRecursoButton = new JButton("Adicionar Recurso");
        adicionarRecursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarRecurso();
            }
        });

        JButton editarRecursoButton = new JButton("Editar Recurso");
        editarRecursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarRecurso();
            }
        });

        JButton removerRecursoButton = new JButton("Remover Recurso");
        removerRecursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerRecurso();
            }
        });

        painelBotoesRecursos.add(adicionarRecursoButton);
        painelBotoesRecursos.add(editarRecursoButton);
        painelBotoesRecursos.add(removerRecursoButton);

        painelRecursos.add(painelBotoesRecursos, BorderLayout.SOUTH);

        // Adicionar painéis à tela
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelProjetos, painelRecursos);
        splitPane.setDividerLocation(300);

        add(splitPane, BorderLayout.CENTER);

        // Painel de calcular mochila
        JPanel painelDetalhesProjeto = new JPanel();
        painelDetalhesProjeto.setLayout(new BorderLayout());

        JButton calcularMochilaButton = new JButton("Calcular Mochila");
        calcularMochilaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularMochila();
            }
        });

        painelDetalhesProjeto.add(calcularMochilaButton, BorderLayout.NORTH);
        add(painelDetalhesProjeto, BorderLayout.SOUTH);
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
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para remover.");
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
        if (recursoSelecionado != null) {
            Projeto projetoSelecionado = listaProjetos.getSelectedValue();
            if (projetoSelecionado != null) {
                projetoSelecionado.removerRecurso(recursoSelecionado.getId());
                modeloRecursos.removeElement(recursoSelecionado);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um recurso para remover.");
        }
    }

    private void calcularMochila() {
        Projeto projetoSelecionado = listaProjetos.getSelectedValue();
        if (projetoSelecionado != null) {
            List<Recurso> recursos = projetoSelecionado.getRecursos();
            double orcamento = projetoSelecionado.getOrcamento();

            // Criando a instância do algoritmo de mochila
            MochilaAlgoritmoEstrela algoritmo = new MochilaAlgoritmoEstrela();
            List<Recurso> recursosOtimizados = algoritmo.otimizarRecursos(recursos, orcamento);

            // Exibindo os resultados
            StringBuilder resultado = new StringBuilder();
            resultado.append("Orçamento do Projeto: ").append(orcamento).append("\n");
            resultado.append("Recursos Otimizados (Total de " + recursosOtimizados.size() + "):\n");

            double custoTotal = 0;
            double valorTotal = 0;

            for (Recurso recurso : recursosOtimizados) {
                resultado.append("Recurso: ").append(recurso.getNome()).append("\n")
                        .append("  Custo: ").append(recurso.getCusto()).append("\n")
                        .append("  Valor Agregado: ").append(recurso.getValorAgregado()).append("\n");
                custoTotal += recurso.getCusto();
                valorTotal += recurso.getValorAgregado();
            }

            resultado.append("Custo Total: ").append(custoTotal).append("\n");
            resultado.append("Valor Total: ").append(valorTotal).append("\n");

            JOptionPane.showMessageDialog(this, resultado.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para calcular a mochila.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfaceGerenciadorDeProjetos frame = new InterfaceGerenciadorDeProjetos();
                frame.setVisible(true);
            }
        });
    }
}
