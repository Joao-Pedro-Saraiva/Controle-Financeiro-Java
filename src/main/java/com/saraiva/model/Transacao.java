package com.saraiva.model;

public class Transacao {
    private int id;
    private String categoria;
    private double valor;
    private String tipo;
    private String data;

    public Transacao(int id, String categoria, double valor, String tipo, String data){
        setId(id);
        setCategoria(categoria);
        setValor(valor);
        setTipo(tipo);
        setData(data);
    }
    @Override 
    public String toString(){
        String transacao = this.categoria + " " + (this.tipo.equals("Receita") ? "+":"-") + this.valor;
        return transacao;
    }
    public int getId() {
        return this.id;
    }
    public String getCategoria() {
        return this.categoria;
    }
    public double getValor() {
        return this.valor;
    }
    public String getTipo() {
        return this.tipo;
    }
    public String getData() {
        return this.data;
    }
    public void setId(int id){
        this.id = id;
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
