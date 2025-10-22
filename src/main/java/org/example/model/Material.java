package org.example.model;

public class Material {

    private int id;
    private String nome;
    private String unidade;
    private int estoque;

    public Material(int id, String nome, String unidade, int estoque) {
        this.id = id;
        this.nome = nome;
        this.unidade = unidade;
        this.estoque = estoque;
    }
    public Material(String nome, String unidade, int estoque) {
        this.nome = nome;
        this.unidade = unidade;
        this.estoque = estoque;
    }
    public Material(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
