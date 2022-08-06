package com.devpression.bazar.repositorio;

import com.devpression.bazar.handle.ConnectionManager;
import com.devpression.bazar.model.Lote;
import com.devpression.bazar.model.OrgaoDonatario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioOD implements RepositorioGenerico<OrgaoDonatario,Integer> {
    private static RepositorioOD myself=null;
    private RepositorioOD(){}
    public static RepositorioOD getCurrentInstance()
    {
        if(myself==null)
            myself=new RepositorioOD();
        return myself;
    }
    @Override
    public void create(OrgaoDonatario orgaoDonatario) throws SQLException, ClassNotFoundException {
        String sql = "insert into orgaodonatario(nome, endereco, telefone, horarioFuncionamento, descricao)"
                + " value(?,?,?,?,?)";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1,orgaoDonatario.getNome());
        pstm.setString(2,orgaoDonatario.getEndereco());
        pstm.setString(3,orgaoDonatario.getTelefone());
        pstm.setString(4,orgaoDonatario.getHorarioFuncionamento());
        pstm.setString(5,orgaoDonatario.getDescricao());
        pstm.execute();
    }

    @Override
    public void update(OrgaoDonatario orgaoDonatario) throws SQLException, ClassNotFoundException {
        String sql = "update orgaodonatario set nome=?,endereco=?,"
                +"telefone=?,horarioFuncionamento=?,descricao=? where id=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1,orgaoDonatario.getNome());
        pstm.setString(2,orgaoDonatario.getEndereco());
        pstm.setString(3,orgaoDonatario.getTelefone());
        pstm.setString(4,orgaoDonatario.getHorarioFuncionamento());
        pstm.setString(5,orgaoDonatario.getDescricao());
        pstm.setInt(6,orgaoDonatario.getId());
        pstm.execute();
    }

    @Override
    public OrgaoDonatario read(Integer integer) throws SQLException, ClassNotFoundException {
        String sql="select * from orgaodonatario where id=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        ResultSet result = pstm.executeQuery();
        if(result.next())
        {
            OrgaoDonatario od = new OrgaoDonatario();
            od.setNome(result.getString("nome"));
            od.setId(result.getInt("id"));
            od.setDescricao(result.getString("descricao"));
            od.setHorarioFuncionamento(result.getString("horarioFuncionamento"));
            od.setEndereco(result.getString("endereco"));
            od.setTelefone(result.getString("telefone"));
            return od;
        }
        return null;
    }


    @Override
    public void delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql="delete from orgaodonatario where id=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,integer);
        pstm.execute();
    }

    @Override
    public List<OrgaoDonatario> readAll() throws SQLException, ClassNotFoundException {
        List<OrgaoDonatario> ods = new ArrayList<>();
        String sql="select * from orgaodonatario";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();
        while(result.next())
        {
            OrgaoDonatario od = new OrgaoDonatario();
            od.setNome(result.getString("nome"));
            od.setId(result.getInt("id"));
            od.setDescricao(result.getString("descricao"));
            od.setHorarioFuncionamento(result.getString("horarioFuncionamento"));
            od.setEndereco(result.getString("endereco"));
            od.setTelefone(result.getString("telefone"));
            ods.add(od);
        }
        return ods;
    }

    public List<OrgaoDonatario> filter(String string) throws SQLException, ClassNotFoundException, ParseException {
        String sql = "Select * from lote as l inner join orgaodonatario as od on l.codigo = od.id and od.nome like ?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1, "%%"+string+"%%");
        List<OrgaoDonatario> ODs = new ArrayList<>();
        ResultSet result = pstm.executeQuery();
        int enable=1;
        while(result.next())
        {
            ODs.add(this.read(result.getInt("id_lote")));
        }
        return ODs;
    }
}
