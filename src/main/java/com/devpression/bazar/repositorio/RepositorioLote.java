package com.devpression.bazar.repositorio;

import com.devpression.bazar.handle.ConnectionManager;
import com.devpression.bazar.model.Lote;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RepositorioLote implements RepositorioGenerico<Lote,Integer> {
    private static RepositorioLote myself= null;
    private RepositorioLote(){}
    public static RepositorioLote getCurrentInstance()
    {
        if(myself==null)
            myself=new RepositorioLote();
        return myself;
    }

    @Override
    public void create(Lote lote) throws SQLException, ClassNotFoundException {
        String sql = "insert into lote(dataentrega,observacao,id_od,codigo,id_of) values (?,?,?,?,?)";
        PreparedStatement pstm  = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setDate(1, (Date) lote.getDataEntrega());
        pstm.setString(2,lote.getObservacao());
        pstm.setInt(3,lote.getIdOD());
        pstm.setInt(4,lote.getCodigo());
        pstm.setInt(5,lote.getIdOF());
        pstm.execute();
    }

    @Override
    public void update(Lote lote) throws SQLException, ClassNotFoundException {
        String sql = "update lote set dataentrega=?,observaca=?,id_od=?,codigo=?,id_of=? where id=?";
        PreparedStatement pstm  = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setDate(1, (Date) lote.getDataEntrega());
        pstm.setString(2,lote.getObservacao());
        pstm.setInt(3,lote.getIdOD());
        pstm.setInt(4,lote.getCodigo());
        pstm.setInt(5,lote.getIdOF());
        pstm.setInt(6,lote.getId());
        pstm.execute();
    }

    @Override
    public Lote read(Integer integer) throws SQLException, ClassNotFoundException, ParseException {
        String sql ="select * from lote where id=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        ResultSet result = pstm.executeQuery();
        if(result.next())
        {
            Lote l = new Lote();
            l.setDataEntrega(new SimpleDateFormat("yyyy-MM-dd").parse(result.getString("dataentrega")));
            l.setCodigo(result.getInt("codigo"));
            l.setIdOD(result.getInt("id_od"));
            l.setId(result.getInt("id"));
            l.setIdOF(result.getInt("id_of"));
            l.setObservacao(result.getString("observacao"));
            l.setOrgaoDonatario(RepositorioOD.getCurrentInstance().read(l.getIdOD()));
            l.setOrgaoFiscal(RepositorioOF.getCurrentInstance().read(l.getIdOF()));
            l.setProduto(RepositorioProduto.getCurrentInstance().read(l.getCodigo()));
            return l;
        }
        return null;
    }

    @Override
    public void delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "delete from lote where id=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        pstm.execute();
    }

    @Override
    public List<Lote> readAll() throws SQLException, ClassNotFoundException, ParseException {
        List<Lote> lotes = new ArrayList<>();
        String sql ="select * from lote";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();
        while(result.next())
        {
            Lote l = new Lote();
            l.setDataEntrega(new SimpleDateFormat("yyyy-MM-dd").parse(result.getString("dataentrega")));
            l.setCodigo(result.getInt("codigo"));
            l.setIdOD(result.getInt("id_od"));
            l.setId(result.getInt("id"));
            l.setIdOF(result.getInt("id_of"));
            l.setObservacao(result.getString("observacao"));
            l.setOrgaoDonatario(RepositorioOD.getCurrentInstance().read(l.getIdOD()));
            l.setOrgaoFiscal(RepositorioOF.getCurrentInstance().read(l.getIdOF()));
            l.setProduto(RepositorioProduto.getCurrentInstance().read(l.getCodigo()));
            lotes.add(l);
        }
        return lotes;
    }
}