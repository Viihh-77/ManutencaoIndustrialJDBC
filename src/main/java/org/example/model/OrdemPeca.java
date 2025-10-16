package org.example.model;

public class OrdemPeca {

    private int idOrdem;
    private int idPeca;
    private double quantidade;

    public OrdemPeca(int idOrdem, int idPeca, double quantidade) {
        this.idOrdem = idOrdem;
        this.idPeca = idPeca;
        this.quantidade = quantidade;
    }

    public int getIdOrdem() {
        return idOrdem;
    }

    public int getIdPeca() {
        return idPeca;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setIdOrdem(int idOrdem) {
        this.idOrdem = idOrdem;
    }

    public void setIdPeca(int idPeca) {
        this.idPeca = idPeca;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
