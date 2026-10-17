package com.saraiva.dialogs;

import com.saraiva.service.Financeiro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public abstract class AbstractDialog {
    Dialog<ButtonType> dialog = new Dialog<>();
    GridPane dialogGrid = new GridPane();
    ChoiceBox<String> choiceTransacoes;
    TextField categoriaTransacaoField = new TextField();
    TextField valorTransacaoField = new TextField();
    ChoiceBox<String> choiceTipoTransacao = new ChoiceBox<>();
    DatePicker dateTransacaoPicker = new DatePicker();
    
    AbstractDialog(){
        dialogGrid.setId("gridpane");
        dialog.getDialogPane().setContent(dialogGrid);
        dialogGrid.getStylesheets().add(getClass().getResource("/dialog.css").toExternalForm());
    }

    ChoiceBox<String> getChoiceTransacoes(Financeiro financeiro){
        ObservableList<String> toStringsTransacaoes = FXCollections.observableArrayList(financeiro.getToStringTransacoes());
        ChoiceBox<String> choiceTransacoes = new ChoiceBox<>(toStringsTransacaoes);
        choiceTransacoes.setValue(toStringsTransacaoes.get(0));
        return choiceTransacoes;
    }
    TextField getCategoriaTransacaoField(){
        categoriaTransacaoField.setPromptText("Categoria");
        return categoriaTransacaoField;
    }
    TextField getValorTransacaoField(){
        valorTransacaoField.setPromptText("100.00");
        return valorTransacaoField;
    }
    ChoiceBox<String> getChoiceTipoTransacao(){
        choiceTipoTransacao.setValue("Despesa");
        choiceTipoTransacao.getItems().addAll("Receita","Despesa");   
        return choiceTipoTransacao;
    }
    DatePicker getDateTransacaoPicker(){
        return dateTransacaoPicker;
    }
    Button getButtonCancelar(){
        Button cancelarDialog = new Button("Cancelar");
        cancelarDialog.setOnAction(cancelar ->{
            dialog.setResult(ButtonType.CANCEL);
            dialog.close();
        });
        return cancelarDialog;
    }
    void fecharDialog(){
        dialog.setResult(ButtonType.CANCEL);
        dialog.close();
    }
}
