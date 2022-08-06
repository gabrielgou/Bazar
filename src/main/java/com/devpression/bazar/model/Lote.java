package com.devpression.bazar.model;

import java.util.Date;
import java.util.List;

public class Lote {
    private int id;
    private Long dataEntrega;
    private String observacao;
    private int idOD;
    private int idOF;
    private int codigo;
    OrgaoDonatario orgaoDonatario=null;
    OrgaoFiscalizador orgaoFiscal = null;
    List<Produto> produto = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Long dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getIdOD() {
        return idOD;
    }

    public void setIdOD(int idOD) {
        this.idOD = idOD;
    }

    public int getIdOF() {
        return idOF;
    }

    public void setIdOF(int idOF) {
        this.idOF = idOF;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public OrgaoDonatario getOrgaoDonatario() {
        return orgaoDonatario;
    }

    public void setOrgaoDonatario(OrgaoDonatario orgaoDonatario) {
        this.orgaoDonatario = orgaoDonatario;
    }

    public OrgaoFiscalizador getOrgaoFiscal() {
        return orgaoFiscal;
    }

    public void setOrgaoFiscal(OrgaoFiscalizador orgaoFiscal) {
        this.orgaoFiscal = orgaoFiscal;
    }

    public List<Produto> getProduto() {
        return produto;
    }

    public void setProduto(List<Produto> produto) {
        this.produto = produto;
    }
}
