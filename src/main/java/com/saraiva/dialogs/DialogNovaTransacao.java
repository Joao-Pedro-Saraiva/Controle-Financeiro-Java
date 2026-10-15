package com.saraiva.dialogs;

import java.time.format.DateTimeFormatter;
import com.saraiva.dao.TransacaoDAO;
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

public class DialogNovaTransacao extends AbstractDialog {
    public Dialog<ButtonType> getDialogNovaTransacao(Financeiro financeiro, TransacaoDAO transacaoDAO){
        dialog.setTitle("Nova Transação");
        TextField categoriaField = getCategoriaTransacaoField();
        TextField valorField = getValorTransacaoField();
        DatePicker datePicker = getDateTransacaoPicker();
        ChoiceBox<String> tipoChoice = getChoiceTipoTransacao();

        Button criarTrancasacao = new Button("Criar Transacao");
        criarTrancasacao.setOnAction(criar->{
            try{
                double valor = Double.parseDouble(valorField.getText());
                String categoria = categoriaField.getText();
                String dataFormatada = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String tipo = tipoChoice.getValue();

                if(tipo.equals("Receita")){
                    System.out.println("Receita Cadastrada");
                    transacaoDAO.adicionarSQL(financeiro.cadastrarReceita(categoria, valor, dataFormatada));
                } else {
                    System.out.println("Despesa Cadastrada");
                    transacaoDAO.adicionarSQL(financeiro.cadastrarDespesa(categoria, valor, dataFormatada));
                }
                fecharDialog();
            } catch(NumberFormatException expetion){
                Alert alertaErro = new Alert(AlertType.ERROR);
                alertaErro.setContentText("Valor Inválido");
                alertaErro.show();
                fecharDialog();
            }
        });
        dialogGrid.add(new Label("Categoria: "), 0, 0);
        dialogGrid.add(categoriaField, 1,0);

        dialogGrid.add(new Label("Valor: "), 0, 1);
        dialogGrid.add(valorField, 1,1);

        dialogGrid.add(new Label("Data: "), 0, 2);
        dialogGrid.add(datePicker, 1, 2);

        dialogGrid.add(new Label("Tipo:"), 0, 3);
        dialogGrid.add(tipoChoice, 1, 3);

        dialogGrid.add(criarTrancasacao, 1, 4);
        dialogGrid.add(getButtonCancelar(), 0, 4);     
        return dialog;
    }
}
