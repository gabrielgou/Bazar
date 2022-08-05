package com.devpression.bazar.repositorio;

import com.devpression.bazar.model.Lote;
import com.devpression.bazar.model.Produto;

public enum RepositorioFactory{
    Produto{
        @Override
        public RepositorioGenerico getInstance()
        {
            return RepositorioProduto.getCurrentInstance();
        }
    },
    Lote{
        @Override
        public RepositorioGenerico getInstance()
        {
            return RepositorioLote.getCurrentInstance();
        }
    },
    OrgaoDonatario{
        @Override
        public RepositorioGenerico getInstance() {
            return RepositorioOD.getCurrentInstance();
        }
    },
    OrgaoFiscaliador{
        @Override
        public RepositorioGenerico getInstance() {
            return RepositorioOF.getCurrentInstance();
        }
    };
    public abstract RepositorioGenerico getInstance();

}
