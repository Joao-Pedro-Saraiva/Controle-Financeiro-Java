package com.saraiva.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                financeiro.cadastrarTransacao(resultSet.getInt("id"),resultSet.getString("categoria"),resultSet.getDouble("valor"),resultSet.getString("tipo"),resultSet.getString("data"));
            }
            return financeiro.getTransacoes();
        } catch(SQLException e){
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    public int adicionarSQL(Transacao transacao){
        int idGerado = 0;
        try(Connection conn = new ConnectionFactory().getConnect()){
            PreparedStatement ps = conn.prepareStatement("INSERT INTO TRANSACAO(categoria, valor, tipo, data) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transacao.getCategoria());
            ps.setDouble(2, transacao.getValor());
            ps.setString(3, transacao.getTipo());
            ps.setString(4, transacao.getData());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGerado = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return idGerado;
    }
    public void editarSQL(Transacao transacao){
        try(Connection conn = new ConnectionFactory().getConnect()){
            PreparedStatement ps = conn.prepareStatement("UPDATE TRANSACAO SET categoria =?, valor =?, tipo =?, data =? WHERE id =?");
            ps.setString(1, transacao.getCategoria());
            ps.setDouble(2, transacao.getValor());
            ps.setString(3, transacao.getTipo());
            ps.setString(4, transacao.getData());

            ps.setInt(5, transacao.getId());
            ps.executeUpdate();
        } catch(SQLException e){
            System.out.println(e);
        }
    }
    public void excluirSQL(Transacao transacao){
        try(Connection conn = new ConnectionFactory().getConnect()){
            PreparedStatement ps = conn.prepareStatement("DELETE FROM transacao WHERE id =?");
            ps.setInt(1, transacao.getId());
            ps.executeUpdate();
        } catch(SQLException e){
            System.out.println(e);
        }  
    }
}
