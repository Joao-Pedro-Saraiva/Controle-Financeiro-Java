package com.saraiva.dialogs;

import com.saraiva.dao.TransacaoDAO;
import com.saraiva.service.Financeiro;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

public class DialogExcluirTransacao extends AbstractDialog{
    public Dialog<ButtonType> getDialogExcluir(Financeiro financeiro, TransacaoDAO transacaoDAO){
        dialog.setTitle("Excluir Transação");
        ChoiceBox<String> choiceTransacoes = getChoiceTransacoes(financeiro);

        Button excluirTransacao = new Button("Excluir");
        excluirTransacao.setOnAction(exclui->{
            String transacao = choiceTransacoes.getValue();
            transacaoDAO.excluirSQL(financeiro.getTransacao(transacao));
            financeiro.excluirTransacao(financeiro.getTransacao(transacao));
            fecharDialog();
        });

        dialogGrid.add(new Label("Transação: "), 0, 0);
        dialogGrid.add(choiceTransacoes, 1, 0);
        
        dialogGrid.add(getButtonCancelar(), 0, 1);
        dialogGrid.add(excluirTransacao, 1, 1);
        return dialog;
    }
}
