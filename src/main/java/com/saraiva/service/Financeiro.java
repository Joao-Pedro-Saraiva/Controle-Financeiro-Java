package com.saraiva.service;

import java.util.ArrayList;
import com.saraiva.model.Transacao;

public class Financeiro {
    private double dinheiro;
    private ArrayList<Transacao> transacoes;
    public Financeiro(){
        this.transacoes = new ArrayList<>();
    }
    public Financeiro(ArrayList<Transacao> transacoes){
        this.transacoes = new ArrayList<>(transacoes);
        atualizarSaldo();
    }

    public Transacao cadastrarTransacao(int id, String categoria, double valor, String tipo, String data){
        Transacao transacao = new Transacao(id, categoria, valor, tipo, data);
        this.transacoes.add(transacao);
        atualizarSaldo();
        return transacao;
    }
    public Transacao cadastrarReceita(String categoria, double valor, String data){
        Transacao transacao = new Transacao(0, categoria, valor, "Receita", data);
        this.transacoes.add(transacao);
        atualizarSaldo();
        return transacao;
    }
    public Transacao cadastrarDespesa(String categoria, double valor, String data){
        Transacao transacao = new Transacao(0, categoria, valor, "Despesa", data);
        this.transacoes.add(transacao);
        atualizarSaldo();
        return transacao;
    }
    public double consultarSaldo(){
        return dinheiro;
    }
    public Transacao editarTransacao(Transacao transacao, String categoria, double valor, String tipo, String data){
        transacao.setCategoria(categoria);
        transacao.setValor(valor);
        transacao.setTipo(tipo);
        transacao.setData(data);
        atualizarSaldo();
        return transacao;
    }
    public void excluirTransacao(Transacao transacao){
        this.transacoes.remove(transacao);
        atualizarSaldo();
    }
    public Transacao getTransacao(int id){
        Transacao transacao = null;
        for(Transacao t : transacoes){
            if(t.getId() == id){
                transacao = t;
            }
        } 
        return transacao;
    }
    private void atualizarSaldo(){
        double valorPositivo = 0;
        double valorNegativo = 0;
        for (Transacao receita : getReceitas()){
            valorPositivo += receita.getValor();
        }
        for (Transacao despesas : getDespesas()){
            valorNegativo += despesas.getValor();
        }
        this.dinheiro = valorPositivo - valorNegativo;
    }
    public ArrayList<String> getToStringTransacoes(){
        ArrayList<String> toStringsTransacoes = new ArrayList<>();
        for(Transacao transacao : this.transacoes){
            toStringsTransacoes.add(transacao.toString());
        }
        return toStringsTransacoes;
    }
    public ArrayList<Transacao> getTransacoes(){
        return transacoes;
    }
    public ArrayList<Transacao> getDespesas() {
        ArrayList<Transacao> despesas = new ArrayList<>();
        for(Transacao transacao : this.transacoes){
            if(transacao.getTipo().equals("Despesa")){
                despesas.add(transacao);
            }
        }
        return despesas;
    }
    public ArrayList<Transacao> getReceitas() {
        ArrayList<Transacao> receitas = new ArrayList<>();
        for(Transacao transacao : this.transacoes){
            if(transacao.getTipo().equals("Receita")){
                receitas.add(transacao);
            }
        }
        return receitas;
    }
}