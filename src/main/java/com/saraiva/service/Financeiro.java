package com.saraiva.service;

import java.util.ArrayList;
import com.saraiva.model.Transacao;

public class Financeiro {
    private double dinheiro;
    private ArrayList<Transacao> transacoes;
    private ArrayList<Transacao> despesas;
    private ArrayList<Transacao> receitas;

    public Financeiro(){
        this.transacoes = new ArrayList<>();
        this.despesas = new ArrayList<>();
        this.receitas = new ArrayList<>();
    }
    public Financeiro(ArrayList<Transacao> transacoes){
        this.transacoes = new ArrayList<>(transacoes);
        this.despesas = new ArrayList<>();
        this.receitas = new ArrayList<>();
        atualizarList();
        atualizarSaldo();
    }

    public Transacao cadastrarTransacao(String categoria, double valor, String tipo, String data){
        if(tipo.equals("Receita")){
            return cadastrarReceita(categoria, valor, data);
        } else if(tipo.equals("Despesa")){
            return cadastrarDespesa(categoria, valor, data);
        } else {
            return null;
        }
    }
    public Transacao cadastrarReceita(String categoria, double valor, String data){
        Transacao transacao = new Transacao(categoria, valor, "Receita", data);
        this.receitas.add(transacao);
        this.transacoes.add(transacao);
        atualizarSaldo();
        return transacao;

    }
    public Transacao cadastrarDespesa(String categoria, double valor, String data){
        Transacao transacao = new Transacao(categoria, valor, "Despesa", data);
        this.despesas.add(transacao);
        this.transacoes.add(transacao);
        atualizarSaldo();
        return transacao;
    }
    public double consultarSaldo(){
        return dinheiro;
    }
    public Transacao editarTransacao(Transacao transacao, String categoria, double valor, String tipo, String data){
        if(!(transacao.getTipo().equals(tipo))){
            if(tipo.equals("Receita")){
                this.despesas.remove(transacao);
                this.receitas.add(transacao);
            } else if (tipo.equals("Despesa")){
                this.receitas.remove(transacao);
                this.despesas.add(transacao);
            }
        }
        transacao.setCategoria(categoria);
        transacao.setValor(valor);
        transacao.setTipo(tipo);
        transacao.setData(data);
        atualizarSaldo();
        return transacao;
    }
    public void excluirTransacao(Transacao transacao){
        this.transacoes.remove(transacao);
        if(transacao.getTipo().equals("Receita")){
            this.receitas.remove(transacao);
        } else if(transacao.getTipo().equals("Despesa")){
            this.despesas.remove(transacao);
        }
        atualizarSaldo();
    }
    public Transacao getTransacao(String toString){
        Transacao transacao = null;
        for(Transacao t : transacoes){
            if(t.toString().equals(toString)){
                transacao = t;
            }
        } 
        return transacao;
    }
    private void atualizarSaldo(){
        double valorPositivo = 0;
        double valorNegativo = 0;
        for (Transacao receita : this.receitas){
            valorPositivo += receita.getValor();
        }
        for (Transacao despesas : this.despesas){
            valorNegativo += despesas.getValor();
        }
        this.dinheiro = valorPositivo - valorNegativo;
    }
    private void atualizarList(){
        for(Transacao transacao : this.transacoes){
            if(transacao.getTipo().equals("Receita")){
                this.receitas.add(transacao);
            } else if (transacao.getTipo().equals("Despesa")){
                this.despesas.add(transacao);
            }
        }
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
        return despesas;
    }
    public ArrayList<Transacao> getReceitas() {
        return receitas;
    }
}