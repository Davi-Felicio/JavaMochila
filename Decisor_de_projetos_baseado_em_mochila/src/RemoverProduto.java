package com.example.interfaceestoque;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

public class RemoverProduto {

    public static void removerProduto(TableView<Produto> table, Estoque estoque) {
        Produto selectedProduto = table.getSelectionModel().getSelectedItem(); // Seleciona o produto na tabela
        if (selectedProduto != null) {
            // Remove o produto da lista da tabela e do estoque
            table.getItems().remove(selectedProduto);
            estoque.removeEstoque(selectedProduto);

            // Mensagem de confirmação
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Produto Removido");
            alert.setHeaderText(null);
            alert.setContentText("Produto \"" + selectedProduto.getNome() + "\" removido com sucesso.");
            alert.showAndWait();
        } else {
            // Exibe um alerta caso nenhum produto esteja selecionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Produto Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um produto para remover.");
            alert.showAndWait();
        }
    }
}

