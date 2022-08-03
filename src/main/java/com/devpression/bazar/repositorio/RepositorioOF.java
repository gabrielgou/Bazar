package com.devpression.bazar.repositorio;

import com.devpression.bazar.handle.ConnectionManager;
import com.devpression.bazar.model.OrgaoFiscalizador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioOF implements RepositorioGenerico<OrgaoFiscalizador,Integer> {
    private static RepositorioOF myself=null;
    private RepositorioOF(){}
    public static RepositorioOF getCurrentInstance()
    {
        if(myself==null)
            myself=new RepositorioOF();
        return myself;
    }
    @Override
    public void create(OrgaoFiscalizador orgaoFiscalizador) throws SQLException, ClassNotFoundException {
        String sql = "insert into orgaofiscalizador(nome, descricao) values(?,?)";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1,orgaoFiscalizador.getNome());
        pstm.setString(2,orgaoFiscalizador.getDescricao());
        pstm.execute();
    }

    @Override
    public void update(OrgaoFiscalizador orgaoFiscalizador) throws SQLException, ClassNotFoundException {
        String sql = "update orgaofiscalizador set nome=?, descricao=? where id=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1,orgaoFiscalizador.getNome());
        pstm.setString(2,orgaoFiscalizador.getDescricao());
        pstm.setInt(3,orgaoFiscalizador.getId());
        pstm.execute();
    }

    @Override
    public OrgaoFiscalizador read(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "select * from orgaofiscalizador where id=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        ResultSet result = pstm.executeQuery();
        if(result.next())
        {
            OrgaoFiscalizador of = new OrgaoFiscalizador();
            of.setId(result.getInt("id"));
            of.setNome(result.getString("nome"));
            of.setDescricao(result.getString("descricao"));
            return of;
        }
        return null;
    }

    @Override
    public void delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "delete from orgaofiscalizador where id=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        pstm.execute();
    }

    @Override
    public List<OrgaoFiscalizador> readAll() throws SQLException, ClassNotFoundException {
        List<OrgaoFiscalizador> ofs = new ArrayList<>();
        String sql = "select * from orgaofiscalizador";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();
        while(result.next())
        {
            OrgaoFiscalizador of = new OrgaoFiscalizador();
            of.setId(result.getInt("id"));
            of.setNome(result.getString("nome"));
            of.setDescricao(result.getString("descricao"));
            ofs.add(of);
        }
        return ofs;
    }
}
