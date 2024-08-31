package Model;

import Model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int idVenda;
    private int idCliente;
    private LocalDate dataVenda;
    private BigDecimal totalVenda;
    private List<ItensVenda> produtos;  // Lista de produtos na venda

    // Construtor padrão
    public Venda() {
        this.produtos = new ArrayList<>();
    }

    // Construtor completo
    public Venda(int idVenda, int idCliente, LocalDate dataVenda, BigDecimal totalVenda) {
        this.idVenda = idVenda;
        this.idCliente = idCliente;
        this.dataVenda = dataVenda;
        this.totalVenda = totalVenda;
        this.produtos = new ArrayList<>();
    }

    // Getters e Setters
    public int getIdVenda() {
        return idVenda;
    }

    public void setId_venda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getId_cliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getTotal_venda() {
        return totalVenda;
    }

    public void setTotalVenda(BigDecimal totalVenda) {
        this.totalVenda = totalVenda;
    }

    public List<ItensVenda> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ItensVenda> produtos) {
        this.produtos = produtos;
    }

    // Método para adicionar um produto à venda
    public void adicionarProduto(ItensVenda vendaProduto) {
        this.produtos.add(vendaProduto);
        atualizarTotalVenda();  // Atualiza o total da venda ao adicionar um produto
    }

    // Método para calcular e atualizar o total da venda
    public void atualizarTotalVenda() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItensVenda produto : produtos) {
            total = total.add(produto.calcularTotal());
        }
        this.totalVenda = total;
    }
}
