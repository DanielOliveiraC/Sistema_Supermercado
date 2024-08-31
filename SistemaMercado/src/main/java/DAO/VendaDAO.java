package DAO;

import Model.*;
import Model.ItensVenda;
import BancoDeDados.DriverMySQL;
import DAO.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendaDAO {
    ProdutosDAO produtosDAO = new ProdutosDAO();
    ClientesDAO clientesDAO = new ClientesDAO();
    ItensVendaDAO itensVendaDAO = new ItensVendaDAO();
    private Connection con = DriverMySQL.getConnection();

    public VendaDAO() {}

    public int inserirVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO venda (id_cliente, data_venda, total_venda) VALUES (?, NOW(), ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, venda.getId_cliente());
            stmt.setBigDecimal(2, venda.getTotal_venda());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idVenda = rs.getInt(1);
                venda.setId_venda(idVenda);
                return idVenda;
            } else {
                throw new SQLException("Erro ao inserir a venda.");
            }
        }
    }

    public void inserirItensVenda(int idVenda, List<ItensVenda> itensVenda) throws SQLException {
        String sql = "INSERT INTO ItensVenda (id_produto, id_venda, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (ItensVenda item : itensVenda) {
                stmt.setInt(1, item.getIdProduto());
                stmt.setInt(2, idVenda);
                stmt.setInt(3, item.getQuantidade());
                stmt.setBigDecimal(4, item.getPrecoUnitario());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public void exibirVendaCliente(int idCliente) throws SQLException {
        String sql = "SELECT v.id_venda, v.data_venda, v.total_venda, " +
                "iv.id_produto, iv.quantidade, iv.preco_unit " +
                "FROM venda v " +
                "JOIN itensVenda iv ON v.id_venda = iv.id_venda " +
                "WHERE v.id_cliente = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Venda> vendas = new ArrayList<>();
                int idVendaAtual = -1;
                Venda vendaAtual = null;

                while (rs.next()) {
                    int idVenda = rs.getInt("id_venda");

                    if (idVenda != idVendaAtual) {
                        vendaAtual = new Venda();
                        vendaAtual.setId_venda(idVenda);
                        vendaAtual.setDataVenda(LocalDate.from(rs.getTimestamp("data_venda").toLocalDateTime()));
                        vendaAtual.setTotalVenda(rs.getBigDecimal("total_venda"));
                        vendas.add(vendaAtual);
                        idVendaAtual = idVenda;
                    }

                    ItensVenda item = new ItensVenda();
                    item.setIdProduto(rs.getInt("id_produto"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setPrecoUnitario(rs.getBigDecimal("preco_unit"));

                    if (vendaAtual != null) {
                        vendaAtual.adicionarProduto(item);
                    }
                }

                // Exibe as vendas do cliente
                for (Venda venda : vendas) {
                    System.out.println("ID da Venda: " + venda.getIdVenda());
                    System.out.println("Data da Venda: " + venda.getDataVenda());
                    System.out.println("Total da Venda: " + venda.getTotal_venda());
                    System.out.println("Itens da Venda:");

                    for (ItensVenda item : venda.getProdutos()) {
                        System.out.println("- Produto ID: " + item.getIdProduto());
                        System.out.println("  Quantidade: " + item.getQuantidade());
                        System.out.println("  Preço Unitário: " + item.getPrecoUnitario());
                    }

                    System.out.println("------------------------------------");
                }

                if (vendas.isEmpty()) {
                    System.out.println("Nenhuma venda encontrada para o cliente ID " + idCliente);
                }
            }
        }
    }
    public void realizarVenda() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Venda venda = new Venda();

        // Listar clientes e selecionar o cliente
        clientesDAO.listar_clientes();
        System.out.print("Digite o ID do cliente: ");
        int idCliente = scanner.nextInt();
        venda.setIdCliente(idCliente);
        venda.setDataVenda(LocalDate.now());

        // Adicionar produtos à venda
        boolean adicionarMaisProdutos = true;
        while (adicionarMaisProdutos) {
            produtosDAO.listar_produtos();
            System.out.print("Digite o ID do produto: ");
            int idProduto = scanner.nextInt();

            System.out.print("Digite a quantidade: ");
            int quantidade = scanner.nextInt();

            int estoqueDisponivel = produtosDAO.obterEstoque(idProduto);
            if (quantidade > estoqueDisponivel) {
                System.out.println("Erro: Quantidade solicitada maior que o estoque disponível. Estoque atual: " + estoqueDisponivel);
                continue; // Pule para o próximo loop para tentar outro produto ou quantidade
            }
            produtosDAO.atualizarEstoque(idProduto, quantidade);

            BigDecimal precoUnitario = produtosDAO.getPreco(idProduto);

            ItensVenda itensVenda = new ItensVenda();
            itensVenda.setIdProduto(idProduto);
            itensVenda.setQuantidade(quantidade);
            itensVenda.setPrecoUnitario(precoUnitario);

            venda.adicionarProduto(itensVenda);

            System.out.print("Deseja adicionar mais produtos? (s/n): ");
            adicionarMaisProdutos = scanner.next().equalsIgnoreCase("s");
        }

        // Inserir a venda no banco de dados
        int idVenda = inserirVenda(venda);
        venda.setId_venda(idVenda);

        // Inserir os itens da venda no banco de dados
        for (ItensVenda produto : venda.getProdutos()) {
            produto.setIdVenda(venda.getIdVenda());
            itensVendaDAO.adicionarProdutoAVenda(produto);
        }

        System.out.println("Venda cadastrada com sucesso!");
        System.out.println(venda);
    }
}
