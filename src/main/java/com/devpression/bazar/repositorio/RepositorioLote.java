package com.devpression.bazar.repositorio;

import com.devpression.bazar.handle.ConnectionManager;
import com.devpression.bazar.model.Lote;
import com.devpression.bazar.model.Produto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
        String sql = "SELECT id_lote FROM lote ORDER BY id_lote DESC LIMIT 1";
        PreparedStatement pstm  = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();
        if(result.next())
        {
            lote.setId(result.getInt("id_lote")+1);
        }
        for(Produto p: lote.getProduto()) {
            sql = "insert into lote(id_lote,dataentrega,observacao,id_od,codigo,id_of) values (?,?,?,?,?,?)";
            pstm  = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            pstm.setInt(1,lote.getId());
            pstm.setLong(2,  lote.getDataEntrega());
            pstm.setString(3,lote.getObservacao());
            pstm.setInt(4,lote.getIdOD());
            pstm.setInt(5, p.getCodigo());
            pstm.setInt(6,lote.getIdOF());
            pstm.execute();
        }
    }

    @Override
    public void update(Lote lote) throws SQLException, ClassNotFoundException {
        String sql = "update lote set dataentrega=?,observaca=?,id_od=?,codigo=?,id_of=? where id_lote=?";
        PreparedStatement pstm  = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setLong(1, lote.getDataEntrega());
        pstm.setString(2,lote.getObservacao());
        pstm.setInt(3,lote.getIdOD());
        pstm.setInt(4,lote.getCodigo());
        pstm.setInt(5,lote.getIdOF());
        pstm.setInt(6,lote.getId());
        pstm.execute();
    }

    @Override
    public Lote read(Integer integer) throws SQLException, ClassNotFoundException, ParseException {
        String sql ="select * from lote where id_lote=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        ResultSet result = pstm.executeQuery();

        if(result.next())
        {
            Lote l = new Lote();
            List<Produto> p = new ArrayList<>();
            l.setDataEntrega(result.getLong("dataentrega"));
            l.setCodigo(result.getInt("codigo"));
            l.setIdOD(result.getInt("id_od"));
            l.setId(result.getInt("id_lote"));
            l.setIdOF(result.getInt("id_of"));
            l.setObservacao(result.getString("observacao"));
            l.setOrgaoDonatario(RepositorioOD.getCurrentInstance().read(l.getIdOD()));
            l.setOrgaoFiscal(RepositorioOF.getCurrentInstance().read(l.getIdOF()));
            p.add(RepositorioProduto.getCurrentInstance().read(l.getCodigo()));
            while(result.next()) {
                p.add(RepositorioProduto.getCurrentInstance().read(result.getInt("codigo")));
            }
            l.setProduto(p);
            return l;
        }
        return null;
    }

    @Override
    public void delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "delete from lote where id_lote=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        pstm.execute();
    }


    public List<Lote> filter(String string) throws SQLException, ClassNotFoundException, ParseException {
        String sql = "Select * from lote as l inner join produto as p on l.codigo = p.codigo and p.nome like ?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1, "%%"+string+"%%");
        List<Lote> lotes = new ArrayList<>();
        ResultSet result = pstm.executeQuery();
        int enable=1;
        while(result.next())
        {
            lotes.add(this.read(result.getInt("id_lote")));
        }
        return lotes;
    }

    public List<Lote> filterOD(String string) throws SQLException, ClassNotFoundException, ParseException {
        String sql = "Select * from lote as l inner join orgaodonatario as od on l.codigo = od.id and od.nome like ?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1, "%%"+string+"%%");
        List<Lote> lotes = new ArrayList<>();
        ResultSet result = pstm.executeQuery();
        int enable=1;
        while(result.next())
        {
            lotes.add(this.read(result.getInt("id_lote")));
        }
        return lotes;
    }

    public List<Lote> filterOF(String string) throws SQLException, ClassNotFoundException, ParseException {
        String sql = "Select * from lote as l inner join orgaofiscalizador as orf on l.codigo = orf.id and orf.nome like ?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1, "%%"+string+"%%");
        List<Lote> lotes = new ArrayList<>();
        ResultSet result = pstm.executeQuery();
        int enable=1;
        while(result.next())
        {
            lotes.add(this.read(result.getInt("id_lote")));
        }
        return lotes;
    }


    @Override
    public List<Lote> readAll() throws SQLException, ClassNotFoundException, ParseException {
        List<Lote> lotes = new ArrayList<>();
        String sql ="select * from lote";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();
        int enable=1;
        while(result.next())
        {
            for(Lote aux : lotes)
            {
                if(aux.getId()==result.getInt("id_lote"))
                {
                    enable=0;
                    aux.getProduto().add(RepositorioProduto.getCurrentInstance().read(result.getInt("codigo")));
                }
            }
            if(enable==1) {
                Lote l = new Lote();
                l.setDataEntrega(result.getLong("dataentrega"));
                l.setCodigo(result.getInt("codigo"));
                l.setIdOD(result.getInt("id_od"));
                l.setId(result.getInt("id_lote"));
                l.setIdOF(result.getInt("id_of"));
                l.setObservacao(result.getString("observacao"));
                l.setOrgaoDonatario(RepositorioOD.getCurrentInstance().read(l.getIdOD()));
                l.setOrgaoFiscal(RepositorioOF.getCurrentInstance().read(l.getIdOF()));
                List<Produto> p = new ArrayList<>();
                p.add(RepositorioProduto.getCurrentInstance().read(l.getCodigo()));
                l.setProduto(p);
                lotes.add(l);
            }
            enable=1;
        }
        return lotes;
    }
}
