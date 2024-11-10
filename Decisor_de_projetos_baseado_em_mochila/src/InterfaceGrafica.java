import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InterfaceGrafica extends JFrame {
    // Componentes de interface
    private JTextField nomeRecursoField;
    private JTextField custoField;
    private JTextField valorField;
    private JTextField nomeProjetoField;
    private JTextField orçamentoField;
    private JTextArea resultadosArea;
    private JComboBox<Projeto> projetoComboBox;
    private JButton adicionarRecursoButton;
    private JButton otimizarButton;
    private JButton adicionarProjetoButton;

    private List<Recurso> listaDeRecursos;
    private List<Projeto> listaDeProjetos;

    // Construtor
    public InterfaceGrafica() {
        listaDeRecursos = new ArrayList<>();
        listaDeProjetos = new ArrayList<>();

        // Inicializando a interface
        setLayout(new BorderLayout());

        // Painel para campos de entrada (com GridBagLayout)
        JPanel painelEntrada = new JPanel();
        painelEntrada.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adiciona um espaçamento entre os componentes

        // Nome do Projeto
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelEntrada.add(new JLabel("Nome do Projeto:"), gbc);
        nomeProjetoField = new JTextField(20);
        gbc.gridx = 1;
        painelEntrada.add(nomeProjetoField, gbc);

        // Orçamento do Projeto
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelEntrada.add(new JLabel("Orçamento do Projeto:"), gbc);
        orçamentoField = new JTextField(20);
        gbc.gridx = 1;
        painelEntrada.add(orçamentoField, gbc);

        // Adicionar Projeto
        adicionarProjetoButton = new JButton("Adicionar Projeto");
        gbc.gridx = 1;
        gbc.gridy = 2;
        painelEntrada.add(adicionarProjetoButton, gbc);

        // Nome do Recurso
        gbc.gridx = 0;
        gbc.gridy = 3;
        painelEntrada.add(new JLabel("Nome do Recurso:"), gbc);
        nomeRecursoField = new JTextField(20);
        gbc.gridx = 1;
        painelEntrada.add(nomeRecursoField, gbc);

        // Custo
        gbc.gridx = 0;
        gbc.gridy = 4;
        painelEntrada.add(new JLabel("Custo do Recurso:"), gbc);
        custoField = new JTextField(20);
        gbc.gridx = 1;
        painelEntrada.add(custoField, gbc);

        // Valor Agregado
        gbc.gridx = 0;
        gbc.gridy = 5;
        painelEntrada.add(new JLabel("Valor Agregado:"), gbc);
        valorField = new JTextField(20);
        gbc.gridx = 1;
        painelEntrada.add(valorField, gbc);

        // Adicionar Recurso
        adicionarRecursoButton = new JButton("Adicionar Recurso");
        gbc.gridx = 1;
        gbc.gridy = 6;
        painelEntrada.add(adicionarRecursoButton, gbc);

        // ComboBox para selecionar projeto
        gbc.gridx = 0;
        gbc.gridy = 7;
        painelEntrada.add(new JLabel("Selecione Projeto:"), gbc);
        projetoComboBox = new JComboBox<>();
        gbc.gridx = 1;
        painelEntrada.add(projetoComboBox, gbc);

        // Botão para otimizar
        otimizarButton = new JButton("Otimizar");
        gbc.gridx = 1;
        gbc.gridy = 8;
        painelEntrada.add(otimizarButton, gbc);

        // Área de resultados
        resultadosArea = new JTextArea(10, 30);
        resultadosArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadosArea);

        // Adiciona os componentes à janela
        add(painelEntrada, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Configuração da janela
        setTitle("Interface de Otimização de Recursos");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Comportamento dos botões
        adicionarProjetoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProjeto();
            }
        });

        adicionarRecursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarRecurso();
            }
        });

        otimizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                otimizarRecursos();
            }
        });
    }

    // Função para adicionar um projeto
    private void adicionarProjeto() {
        try {
            String nomeProjeto = nomeProjetoField.getText();
            double orçamento = Double.parseDouble(orçamentoField.getText());
            if (!nomeProjeto.isEmpty()) {
                Projeto projeto = new Projeto(nomeProjeto, orçamento);
                listaDeProjetos.add(projeto);
                projetoComboBox.addItem(projeto);
                nomeProjetoField.setText("");
                orçamentoField.setText("");
                JOptionPane.showMessageDialog(this, "Projeto adicionado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, insira um nome para o projeto.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor válido para o orçamento.");
        }
    }

    // Função para adicionar um recurso a um projeto
    private void adicionarRecurso() {
        try {
            String nomeRecurso = nomeRecursoField.getText();
            double custo = Double.parseDouble(custoField.getText());
            double valorAgregado = Double.parseDouble(valorField.getText());

            // Criar o recurso
            Recurso recurso = new Recurso(nomeRecurso, custo, valorAgregado);

            // Adicionar o recurso ao projeto selecionado
            Projeto projetoSelecionado = (Projeto) projetoComboBox.getSelectedItem();
            if (projetoSelecionado != null) {
                projetoSelecionado.adicionarRecurso(recurso);
                listaDeRecursos.add(recurso);
                JOptionPane.showMessageDialog(this, "Recurso adicionado ao projeto.");
                nomeRecursoField.setText("");
                custoField.setText("");
                valorField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um projeto.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores válidos para custo e valor agregado.");
        }
    }

    // Função para otimizar os recursos e calcular a eficiência usando o algoritmo MochilaEstrela
    private void otimizarRecursos() {
        try {
            Projeto projetoSelecionado = (Projeto) projetoComboBox.getSelectedItem();

            if (projetoSelecionado != null) {
                double limiteCusto = projetoSelecionado.getOrcamento();  // Orçamento do projeto

                // Chamar o algoritmo de otimização (MochilaEstrela)
                MochilaAlgoritmoEstrela algoritmo = new MochilaAlgoritmoEstrela();
                List<Recurso> recursosSelecionados = algoritmo.otimizarRecursos(projetoSelecionado.getRecursos(), limiteCusto);

                // Calcular a eficiência do projeto
                double custoTotal = 0;
                double valorTotal = 0;
                for (Recurso recurso : recursosSelecionados) {
                    custoTotal += recurso.getCusto();
                    valorTotal += recurso.getValorAgregado();
                }
                double eficiencia = valorTotal / custoTotal;

                // Exibir os resultados
                resultadosArea.setText("Recursos Selecionados para o Projeto: " + projetoSelecionado.getNome() + "\n");
                for (Recurso recurso : recursosSelecionados) {
                    resultadosArea.append("Nome: " + recurso.getNome() + ", Custo: " + recurso.getCusto() + ", Valor: " + recurso.getValorAgregado() + "\n");
                }
                resultadosArea.append("\nEficiência do Projeto: " + eficiencia);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um projeto.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao otimizar os recursos.");
        }
    }

    // Método principal para rodar a interface
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGrafica().setVisible(true);
            }
        });
    }
}
