package org.example.model;

import java.time.LocalDate;

public class OrdemManutencao {

    private int id;
    private Maquina idMaquina;
    private Tecnico idTecnico;
    private LocalDate dataSolicitacao;
    private String status;

    public OrdemManutencao(int id, Maquina idMaquina, Tecnico idTecnico, LocalDate dataSolicitacao, String status) {
        this.id = id;
        this.idMaquina = idMaquina;
        this.idTecnico = idTecnico;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public OrdemManutencao(Maquina idMaquina, Tecnico idTecnico, LocalDate dataSolicitacao, String status) {
        this.idMaquina = idMaquina;
        this.idTecnico = idTecnico;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Maquina getIdMaquina() {
        return idMaquina;
    }

    public Tecnico getIdTecnico() {
        return idTecnico;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdMaquina(Maquina idMaquina) {
        this.idMaquina = idMaquina;
    }

    public void setIdTecnico(Tecnico idTecnico) {
        this.idTecnico = idTecnico;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
