package org.example.model;

public class NotaEntradaItem {

    private int id;
    private int idNotaEntrada;
    private int idMaterial;
    private int quantidade;

    public NotaEntradaItem(int id, int idNotaEntrada, int idMaterial, int quantidade) {
        this.id = id;
        this.idNotaEntrada = idNotaEntrada;
        this.idMaterial = idMaterial;
        this.quantidade = quantidade;
    }
    public NotaEntradaItem(int idNotaEntrada, int idMaterial, int quantidade) {
        this.idNotaEntrada = idNotaEntrada;
        this.idMaterial = idMaterial;
        this.quantidade = quantidade;
    }

    public NotaEntradaItem() {
        this.id = 0;
        this.idNotaEntrada = 0;
        this.idMaterial = 0;
        this.quantidade = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdNotaEntrada() {
        return idNotaEntrada;
    }

    public void setIdNotaEntrada(int idNotaEntrada) {
        this.idNotaEntrada = idNotaEntrada;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
