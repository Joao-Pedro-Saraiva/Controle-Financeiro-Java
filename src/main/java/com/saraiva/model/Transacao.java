package com.saraiva.model;

public class Transacao {
    private String categoria;
    private double valor;
    private String tipo;
    private String data;

    public Transacao(String categoria, double valor, String tipo, String data){
        this.categoria = categoria;
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
    }
    @Override 
    public String toString(){
        String transacao = this.categoria + " " + (this.tipo.equals("Receita") ? "+":"-") + this.valor;
        return transacao;
    }
    public String getCategoria() {
        return categoria;
    }
    public double getValor() {
        return valor;
    }
    public String getTipo() {
        return tipo;
    }
    public String getData() {
        return data;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setData(String data) {
        this.data = data;
    }
}
