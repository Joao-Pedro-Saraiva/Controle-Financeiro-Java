package com.saraiva.dialogs;

import java.time.format.DateTimeFormatter;
import com.saraiva.dao.TransacaoDAO;
import com.saraiva.model.Transacao;
import com.saraiva.service.Financeiro;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DialogEditarTransacao extends AbstractDialog{
    public Dialog<ButtonType> getDialogEditarTransacao(Financeiro financeiro, TransacaoDAO transacaoDAO){
        dialog.setTitle("Editar Transação");
        ChoiceBox<String> choiceTransacoes = getChoiceTransacoes(financeiro);
        TextField categoriaField = getCategoriaTransacaoField();
        TextField valorField = getValorTransacaoField();
        DatePicker datePicker = getDateTransacaoPicker();
        ChoiceBox<String> tipoChoice = getChoiceTipoTransacao();;

        Button editarTransacao = new Button("Editar Transação");
        editarTransacao.setOnAction(editar ->{
            try{
                int indice = choiceTransacoes.getSelectionModel().getSelectedIndex();
                Transacao transacao = financeiro.getTransacoes().get(indice);
                double valor = Double.parseDouble(valorField.getText());
                String categoria = categoriaField.getText();
                String dataFormatada = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String tipo = tipoChoice.getValue();
                
                financeiro.editarTransacao(financeiro.getTransacao(transacao.getId()), categoria, valor, tipo, dataFormatada);
                transacaoDAO.editarSQL(financeiro.getTransacao(transacao.getId()));
                fecharDialog();
            } catch(NumberFormatException expetion){
                Alert alertaErro = new Alert(AlertType.ERROR);
                alertaErro.setContentText("Valor Inválido");
                alertaErro.show();
                fecharDialog();
            }
        });
        dialogGrid.add(new Label("Transação: "), 0, 0);
        dialogGrid.add(choiceTransacoes, 1, 0);

        dialogGrid.add(new Label("Categoria: "), 0, 1);
        dialogGrid.add(categoriaField, 1,1);

        dialogGrid.add(new Label("Valor: "), 0, 2);
        dialogGrid.add(valorField, 1,2);

        dialogGrid.add(new Label("Data: "), 0, 3);
        dialogGrid.add(datePicker, 1, 3);

        dialogGrid.add(new Label("Tipo:"), 0, 4);
        dialogGrid.add(tipoChoice, 1, 4);

        dialogGrid.add(editarTransacao, 1, 5);
        dialogGrid.add(getButtonCancelar(), 0, 5);     
        return dialog;
    }
}
