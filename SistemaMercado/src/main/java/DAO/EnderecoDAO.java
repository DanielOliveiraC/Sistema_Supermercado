package DAO;

import Model.Endereco;
import BancoDeDados.DriverMySQL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.Scanner;

public class EnderecoDAO {
    Scanner scanner = new Scanner(System.in);

    public void inserir_endereco(){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            System.out.println("Digite o logradouro (rua/avenida): ");
            String logradouro = scanner.nextLine();
            scanner.nextLine();
            System.out.println("Digite o numero: ");
            String numero = scanner.nextLine();

            System.out.println("Digite o bairro: ");
            String bairro = scanner.nextLine();

            System.out.println("Digite a cidade: ");
            String cidade = scanner.nextLine();
            scanner.nextLine();



            stmt = con.prepareStatement("""
            INSERT INTO endereco (logradouro, numero, bairro, cidade)
            VALUES (?, ?, ?, ?)
            """);
            stmt.setString(1, logradouro);
            stmt.setString(2, numero);
            stmt.setString(3, bairro);
            stmt.setString(4, cidade);


            stmt.execute();
            System.out.println("Endereço cadastrado com sucesso!");
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void exibir_endereco(){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;

        try{
            String c = "SELECT * FROM endereco";
            stmt = con.prepareStatement(c);
            result = stmt.executeQuery();

            while (result.next()){
                int id_endereco = result.getInt("id_endereco");
                String logradouro_end = result.getString("logradouro");
                String numero_end = result.getString("numero");
                String bairro_end = result.getString("bairro");
                String ciadade_end = result.getString("cidade");
                System.out.println("id: " + id_endereco + "   Endereço: " + logradouro_end + ", " + numero_end + ", " + bairro_end + ", " + ciadade_end);

            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }



    public void alterar_endereco() {
        Connection con = DriverMySQL.getConnection();

        exibir_endereco();

        System.out.println("Qual endereco deseja alterar? (id) ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Quais informações deseja alterar? ");
        System.out.println("1 - Logradouro");
        System.out.println("2 - Número");
        System.out.println("3 - Bairro");
        System.out.println("4 - Cidade");
        System.out.println("5 - Todas as informações");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (opcao) {
                case 1:
                    System.out.println("Digite o novo logradouro: ");
                    String logradouro_novo = scanner.nextLine();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE endereco SET logradouro = ? WHERE id_produto = ?")) {
                        stmt.setString(1, logradouro_novo);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;

                case 2:
                    System.out.println("Digite o novo número: ");
                    String num_novo = scanner.nextLine();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE endereco SET numero = ? WHERE id_produto = ?")) {
                        stmt.setString(1, num_novo);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;

                case 3:
                    System.out.println("Digite o novo bairro: ");
                    String bairro_novo = scanner.nextLine();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE endereco SET bairro = ? WHERE id_produto = ?")) {
                        stmt.setString(1, bairro_novo);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;
                case 4:
                    System.out.println("Digite o nova cidade: ");
                    String cidade_novo = scanner.nextLine();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE endereco SET cidade = ? WHERE id_produto = ?")) {
                        stmt.setString(1, cidade_novo);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;
                case 5:
                    System.out.println("Digite o novo logradouro: ");
                    logradouro_novo = scanner.nextLine();
                    System.out.println("Digite o novo número: ");
                    num_novo = scanner.nextLine();
                    System.out.println("Digite o novo bairro: ");
                    bairro_novo = scanner.nextLine();
                    System.out.println("Digite o nova cidade: ");
                    cidade_novo = scanner.nextLine();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE produtos SET nome = ?, preco = ?, estoque = ? WHERE id_produto = ?")) {
                        stmt.setString(1, logradouro_novo);
                        stmt.setString(2, num_novo);
                        stmt.setString(3, bairro_novo);
                        stmt.setString(4, cidade_novo);
                        stmt.executeUpdate();
                    }
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Erro!" + e.getMessage());
        }

    }


    public void excluir_endereco(){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        exibir_endereco();

        System.out.println("Qual endereoc deve ser excluído? (id) ");
        int id = scanner.nextInt();

        try {
            stmt = con.prepareStatement("DELETE FROM endereco WHERE id_endereco=" + id);
            stmt.execute();
            System.out.println("Deletado com sucesso!");
        } catch (SQLException e){
            System.out.println("Erro! " + e.getMessage());
        }
    }
}
