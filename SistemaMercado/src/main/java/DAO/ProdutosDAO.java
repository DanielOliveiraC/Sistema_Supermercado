package DAO;

import Model.Produtos;
import BancoDeDados.DriverMySQL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProdutosDAO {
    Connection con = DriverMySQL.getConnection();
    PreparedStatement stmt = null;
    Scanner scanner = new Scanner(System.in);

    public int obterEstoque(int idProduto) throws SQLException {
        String sql = "SELECT estoque FROM produtos WHERE id_produto = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("estoque");
            } else {
                throw new SQLException("Produto não encontrado.");
            }
        }
    }

    public void atualizarEstoque(int idProduto, int quantidadeVendida) throws SQLException {
        String sql = "UPDATE produtos SET estoque = estoque - ? WHERE id_produto = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, quantidadeVendida);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();
        }
    }
    public void inserir_produto(){


        try {
            System.out.println("Digite o nome do Produto: ");
            String nome_raiz = scanner.nextLine();
            System.out.println("Digite o estoque do Produto: ");
            int estoque_raiz = scanner.nextInt();
            System.out.println("Digite o preço do Produto: ");
            BigDecimal preco_raiz = scanner.nextBigDecimal();

            stmt = con.prepareStatement("""
            INSERT INTO produtos (nome, estoque, preco)
            VALUES (?, ?, ?)
            """);
            stmt.setString(1, nome_raiz);
            stmt.setInt(2, estoque_raiz);
            stmt.setBigDecimal(3, preco_raiz);


            stmt.execute();
            System.out.println("Produto cadastrado com sucesso!");
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void listar_produtos(){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;

        try{
            String c = "SELECT * FROM produtos";
            stmt = con.prepareStatement(c);
            result = stmt.executeQuery();

            while (result.next()){
                int id_produto = result.getInt("id_produto");
                String nome_produto = result.getString("nome");
                BigDecimal preco_produto = result.getBigDecimal("preco");
                int estoque_produto = result.getInt("estoque");
                System.out.println("__________________________________________________");
                System.out.println("Id: " + id_produto);
                System.out.println("Nome: " + nome_produto);
                System.out.println("Preço (und): " + preco_produto);
                System.out.println("Estoque: " + estoque_produto);
                System.out.println("__________________________________________________");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void excluir_produto(){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        listar_produtos();

        System.out.println("Qual produto deve ser excluído? (id) ");
        int id = scanner.nextInt();

        try {
            stmt = con.prepareStatement("DELETE FROM produtos WHERE id_produto=" + id);
            stmt.execute();
            System.out.println("Deletado com sucesso!");
        } catch (SQLException e){
            System.out.println("Erro! " + e.getMessage());
        }
    }

    public void alterar_produto(){
        Connection con = DriverMySQL.getConnection();

        listar_produtos();

        System.out.println("Qual produto deseja alterar? (id) ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Quais informações deseja alterar? ");
        System.out.println("1 - Nome");
        System.out.println("2 - Preço");
        System.out.println("3 - Estoque");
        System.out.println("4 - Todas informações");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (opcao) {
                case 1:
                    System.out.println("Digite o novo nome: ");
                    String nome_novo = scanner.nextLine();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE produtos SET nome = ? WHERE id_produto = ?")) {
                        stmt.setString(1, nome_novo);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;

                case 2:
                    System.out.println("Digite o novo preço: ");
                    BigDecimal preco_novo = scanner.nextBigDecimal();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE produtos SET preco = ? WHERE id_produto = ?")) {
                        stmt.setBigDecimal(1, preco_novo);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;

                case 3:
                    System.out.println("Digite o novo Estoque: ");
                    int estoque_novo = scanner.nextInt();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE produtos SET estoque = ? WHERE id_produto = ?")) {
                        stmt.setInt(1, estoque_novo);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;

                case 4:
                    System.out.println("Digite o novo nome: ");
                    nome_novo = scanner.nextLine();
                    System.out.println("Digite o novo preço: ");
                    preco_novo = scanner.nextBigDecimal();
                    System.out.println("Digite o novo Estoque: ");
                    estoque_novo = scanner.nextInt();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE produtos SET nome = ?, preco = ?, estoque = ? WHERE id_produto = ?")) {
                        stmt.setString(1, nome_novo);
                        stmt.setBigDecimal(2, preco_novo);
                        stmt.setInt(3, estoque_novo);
                        stmt.setInt(4, id);
                        stmt.executeUpdate();
                    }
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } catch (SQLException e){
            System.out.println("Erro!" + e.getMessage());
        }

    }
    public BigDecimal getPreco(int id) throws SQLException {
        String sql = "SELECT preco FROM produtos WHERE id_produto = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("preco");
            } else {
                throw new SQLException("Produto não encontrado.");
            }
        }
    }


}
