package com.devpression.bazar.repositorio;

import com.devpression.bazar.handle.ConnectionManager;
import com.devpression.bazar.model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioProduto implements RepositorioGenerico<Produto, Integer> {
    private static RepositorioProduto myself =null;
    private RepositorioProduto(){}
    public static RepositorioProduto getCurrentInstance(){
        if(myself==null)
            myself=new RepositorioProduto();
        return myself;
    }
    @Override
    public void create(Produto produto) throws SQLException, ClassNotFoundException {
        String sql="insert into produto(nome, descricao) values(?,?)";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1, produto.getNome());
        pstm.setString(2, produto.getDescricao());
        pstm.execute();
    }

    @Override
    public void update(Produto produto) throws SQLException, ClassNotFoundException {
        String sql="update produto set nome=?, descricao=? where codigo=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1, produto.getNome());
        pstm.setString(2, produto.getDescricao());
        pstm.setInt(3,produto.getCodigo());
        pstm.execute();
    }

    @Override
    public Produto read(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "select * from produto where codigo=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        ResultSet result = pstm.executeQuery();
        if(result.next())
        {
            Produto p = new Produto();
            p.setCodigo(result.getInt("codigo"));
            p.setDescricao(result.getString("descricao"));
            p.setNome(result.getString("nome"));
            return p;
        }
        return null;
    }

    @Override
    public void delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "delete from produto where codigo=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        pstm.execute();
    }

    @Override
    public List<Produto> readAll() throws SQLException, ClassNotFoundException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "select * from produto";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();
        while(result.next())
        {
            Produto p = new Produto();
            p.setCodigo(result.getInt("codigo"));
            p.setDescricao(result.getString("descricao"));
            p.setNome(result.getString("nome"));
            produtos.add(p);
        }
        return produtos;
    }
}
