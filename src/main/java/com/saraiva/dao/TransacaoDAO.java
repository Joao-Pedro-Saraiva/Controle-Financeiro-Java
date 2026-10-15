package com.saraiva.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.saraiva.database.ConnectionFactory;
import com.saraiva.model.Transacao;
import com.saraiva.service.Financeiro;

public class TransacaoDAO {

    public ArrayList<Transacao> getTransacaoSQL(){
        Financeiro financeiro = new Financeiro();
        String sql = "select * from transacao";
        try(Connection conn = new ConnectionFactory().getConnect()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                financeiro.cadastrarTransacao(resultSet.getString("categoria"),resultSet.getDouble("valor"),resultSet.getString("tipo"),resultSet.getString("data"));
            }
            return financeiro.getTransacoes();
            } catch(SQLException e){
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    public Transacao adicionarSQL(Transacao transacao){
        try(Connection conn = new ConnectionFactory().getConnect()){
            PreparedStatement ps = conn.prepareStatement("INSERT INTO TRANSACAO(categoria, valor, tipo, data) VALUES (?,?,?,?)");
            ps.setString(1, transacao.getCategoria());
            ps.setDouble(2, transacao.getValor());
            ps.setString(3, transacao.getTipo());
            ps.setString(4, transacao.getData());
            ps.executeUpdate();
            return transacao;
        }catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }
    public void editarSQL(String categoria, double valor, Transacao transacao){
        try(Connection conn = new ConnectionFactory().getConnect()){
            PreparedStatement ps = conn.prepareStatement("UPDATE TRANSACAO SET categoria =?, valor =?, tipo =?, data =? WHERE categoria =? AND valor =?");
            ps.setString(1, transacao.getCategoria());
            ps.setDouble(2, transacao.getValor());
            ps.setString(3, transacao.getTipo());
            ps.setString(4, transacao.getData());

            ps.setString(5, categoria);
            ps.setDouble(6, valor);
            ps.executeUpdate();
        } catch(SQLException e){
            System.out.println(e);
        }
    }
    public void excluirSQL(Transacao transacao){
        try(Connection conn = new ConnectionFactory().getConnect()){
            PreparedStatement ps = conn.prepareStatement("DELETE FROM transacao WHERE categoria =? AND valor=?");
            ps.setString(1, transacao.getCategoria());
            ps.setDouble(2, transacao.getValor());
            ps.executeUpdate();
        } catch(SQLException e){
            System.out.println(e);
        }  
    }
}
