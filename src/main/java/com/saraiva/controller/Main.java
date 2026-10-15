package com.saraiva.controller;

import com.saraiva.dao.TransacaoDAO;
import com.saraiva.dialogs.DialogEditarTransacao;
import com.saraiva.dialogs.DialogExcluirTransacao;
import com.saraiva.dialogs.DialogNovaTransacao;
import com.saraiva.model.Transacao;
import com.saraiva.service.Financeiro;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main {
    Financeiro financeiro = new Financeiro(new TransacaoDAO().getTransacaoSQL());
    ObservableList<Transacao> listDespesas = FXCollections.observableArrayList(financeiro.getDespesas());
    ObservableList<Transacao> listReceitas = FXCollections.observableArrayList(financeiro.getReceitas());
    TransacaoDAO transacaoDAO = new TransacaoDAO();
 
    public void mostrarTela(Stage stage){
        Pane root = new Pane();
        root.setId("root");
        Scene scene = new Scene(root, 600, 550);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.setTitle("Controle Financeiro");

        TableView<Transacao> tableDespesas = new TableView<>();
        tableDespesas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        VBox despesas = new VBox(tableDespesas);

        tableDespesas.setItems(listDespesas);
        tableDespesas.getColumns().setAll(gerarColuna("Categoria", "categoria"),gerarColuna("Valor", "valor"), gerarColuna("Data","data"));
        despesas.setId("tabelaDespesas");

        TableView<Transacao> tableReceitas = new TableView<>();
        tableReceitas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN); 
        VBox receitas = new VBox(tableReceitas);

        tableReceitas.setItems(listReceitas);
        tableReceitas.getColumns().setAll(gerarColuna("Categoria", "categoria"), gerarColuna("Valor", "valor"), gerarColuna("Data","data"));
        receitas.setId("tabelaReceitas");

        Label titulo = new Label("Controle Financeiro");
        titulo.setId("titulo");
        StringProperty valorSaldo = new SimpleStringProperty(String.format("R$ %.2f", financeiro.consultarSaldo()));
        Label saldo = new Label();
        saldo.textProperty().bind(valorSaldo);
        if(financeiro.consultarSaldo() < 0){
            saldo.setId("saldoNegativo");
        } else {
            saldo.setId("saldo");
        }
        Label textSaldo = new Label("Saldo: ");
        textSaldo.setId("textSaldo");
        Label textReceitas = new Label("Receitas");
        textReceitas.setId("textReceitas");
        Label textDespesas = new Label("Despesas");
        textDespesas.setId("textDespesas");

        //Dialog Excluir Transacao
        Button buttonExcluirTransacao = new Button("Excluir Transação");
        buttonExcluirTransacao.setId("buttonExcluirTransacao");
        buttonExcluirTransacao.setOnAction(e->{
            Dialog<ButtonType> dialogExcluir = new DialogExcluirTransacao().getDialogExcluir(financeiro, transacaoDAO);
            dialogExcluir.show();
            dialogExcluir.setOnCloseRequest(x->{
                atualizarList();
                atualizarSaldo(valorSaldo);
                if(financeiro.consultarSaldo() < 0){
                    saldo.setId("saldoNegativo");
                } else {
                    saldo.setId("saldo");
                }
            });
        });
        
        //Dialog Editar Transacao
        Button buttonEditarTransacao = new Button("Editar Transação");
        buttonEditarTransacao.setId("buttonEditarTransacao");
        buttonEditarTransacao.setOnAction(e ->{
            Dialog<ButtonType> dialogEditar = new DialogEditarTransacao().getDialogEditarTransacao(financeiro, transacaoDAO);       
            dialogEditar.show();
            dialogEditar.setOnCloseRequest(x->{
                atualizarList();
                atualizarSaldo(valorSaldo);
                if(financeiro.consultarSaldo() < 0){
                    saldo.setId("saldoNegativo");
                } else {
                    saldo.setId("saldo");
                }
            });
        });
        
        //Dialog Nova Transacao
        Button buttonNovaTransacao = new Button("Adicionar Transação");
        buttonNovaTransacao.setId("buttonNovaTransacao");
        buttonNovaTransacao.setOnAction(e ->{
            Dialog<ButtonType> dialogNovaTransacao = new DialogNovaTransacao().getDialogNovaTransacao(financeiro, transacaoDAO);
            dialogNovaTransacao.show();
            dialogNovaTransacao.setOnCloseRequest(x->{
                atualizarList();
                atualizarSaldo(valorSaldo);
                if(financeiro.consultarSaldo() < 0){
                    saldo.setId("saldoNegativo");
                } else {
                    saldo.setId("saldo");
                }
            });
        });

        root.getChildren().addAll(titulo, textSaldo, textDespesas, textReceitas, saldo, receitas, despesas, buttonNovaTransacao, buttonEditarTransacao, buttonExcluirTransacao);
        stage.setScene(scene);
        stage.show();
    }

    private TableColumn<Transacao, String> gerarColuna(String titulo, String atributo){
        TableColumn<Transacao, String> column = new TableColumn<>(titulo);
        column.setCellValueFactory(new PropertyValueFactory<>(atributo));
        return column;
    }
    private void atualizarSaldo(StringProperty valorSaldo){
        double saldoAtual = financeiro.consultarSaldo();
        valorSaldo.set(String.format("R$ %.2f", saldoAtual));
    }
    private void atualizarList(){
        listDespesas.setAll(financeiro.getDespesas());
        listReceitas.setAll(financeiro.getReceitas()); 
    }
}