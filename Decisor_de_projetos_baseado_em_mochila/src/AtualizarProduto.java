package com.example.interfaceestoque;




import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AtualizarProduto {

    public static void atualizarProduto(TableView<Produto> table, TextField nomeInput, TextField valorInput,
                                        TextField pesoInput, TextField qntInput, TextField descInput) {
        Produto selectedProduto = table.getSelectionModel().getSelectedItem(); // Seleciona o produto na tabela
        if (selectedProduto != null) {
            try {
                // Atualiza os atributos do produto selecionado
                selectedProduto.setNome(nomeInput.getText());
                selectedProduto.setValor(Double.parseDouble(valorInput.getText()));
                selectedProduto.setPeso(Integer.parseInt(pesoInput.getText()));
                selectedProduto.setQnt(Integer.parseInt(qntInput.getText()));

                table.refresh(); // Atualiza a tabela para refletir as alterações
                nomeInput.clear();
                valorInput.clear();
                pesoInput.clear();
                qntInput.clear();
                descInput.clear(); // Limpa os campos de entrada
            } catch (NumberFormatException ex) {
                // Exibe um alerta se os valores inseridos forem inválidos
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de Entrada");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, insira valores válidos nos campos.");
                alert.showAndWait();
            }
        } else {
            // Exibe um alerta caso nenhum produto esteja selecionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Produto Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um produto para atualizar.");
            alert.showAndWait();
        }
    }
}

