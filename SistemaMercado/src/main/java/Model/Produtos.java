package Model;

import java.math.BigDecimal;

public class Produtos {
    private int id_produto;
    private String nome_produto;
    private BigDecimal preco_produto;
    private int estoque;

    //  Construtor
    public Produtos() {
    }

    // Getters e Setters
    public int getId_produto() {
        return id_produto;
    }
    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public BigDecimal getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(BigDecimal preco_produto) {
        this.preco_produto = preco_produto;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
