package Model;

import java.math.BigDecimal;

public class ItensVenda {
    private int idItensVenda;
    private int idVenda;
    private int idProduto;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItensVenda() {}

    public ItensVenda(int idItensVenda, int idVenda, int idProduto, int quantidade, BigDecimal precoUnitario) {
        this.idItensVenda = idItensVenda;
        this.idVenda = idVenda;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public int getIdItensVenda() {
        return idItensVenda;
    }

    public void setIdItensVenda(int idItensVenda) {
        this.idItensVenda = idItensVenda;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal calcularTotal() {
        return precoUnitario.multiply(new BigDecimal(quantidade));
    }

    @Override
    public String toString() {
        return "ItensVenda{" +
                "idItensVenda=" + idItensVenda +
                ", idVenda=" + idVenda +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                '}';
    }
}
