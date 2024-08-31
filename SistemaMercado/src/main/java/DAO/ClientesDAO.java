package DAO;

import DAO.EnderecoDAO;
import Model.Cliente;
import BancoDeDados.DriverMySQL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientesDAO {
    Scanner scanner = new Scanner(System.in);
    EnderecoDAO enderecoDAO = new EnderecoDAO();
    public void inserir_cliente() {
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            System.out.println("Digite o nome do Cliente: ");
            String nome_cliente = scanner.nextLine();
            System.out.println("Digite o CPF do Cliente: ");
            String cpf_cliente = scanner.nextLine();
            int endereco_cliente = 0;
            while (endereco_cliente == 0) {
                enderecoDAO.exibir_endereco();
                System.out.println("Digite 0 (zero) para dicionar um novo endereço\nDigite o endereço do Cliente: ");
                endereco_cliente = scanner.nextInt();scanner.nextLine();
                if (endereco_cliente == 0) {
                    enderecoDAO.inserir_endereco();
                }
            }
            System.out.println("Digite o telefone do Cliente: ");
            String telefone_cliente = scanner.nextLine();


            stmt = con.prepareStatement("""
            INSERT INTO cliente (nome_cliente, cpf, id_endereco, telefone)
            VALUES (?, ?, ?, ?)
            """);
            stmt.setString(1, nome_cliente);
            stmt.setString(2, cpf_cliente);
            stmt.setInt(3, endereco_cliente);
            stmt.setString(4, telefone_cliente);
            stmt.execute();

            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public void listar_clientes(){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;

        try{
            String c = "select id_cliente, nome_cliente, cpf, telefone, e.logradouro, e.numero, e.bairro, e.cidade from cliente c JOIN endereco e ON c.id_endereco = e.id_endereco;";
            stmt = con.prepareStatement(c);
            result = stmt.executeQuery();

            while (result.next()){
                int id_cliente = result.getInt("id_cliente");
                String nome_cliente = result.getString("nome_cliente");
                String cpf_cliente = result.getString("cpf");
                String telefone_cliente = result.getString("telefone");
                String logradouro = result.getString("logradouro");
                String numero = result.getString("numero");
                String bairro = result.getString("bairro");
                String cidade = result.getString("cidade");

                System.out.println("__________________________________________________");
                System.out.println("Id: " + id_cliente);
                System.out.println("Nome: " + nome_cliente);
                System.out.println("CPF: " + cpf_cliente);
                System.out.println("Telefone: " + telefone_cliente);
                System.out.println("Endereço: " + logradouro + ", " + numero + ", " + bairro + ", " + cidade);
                System.out.println("__________________________________________________");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void excluir_cliente() {
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        listar_clientes();

        System.out.println("Qual cliente deve ser excluído? (id) ");
        int id = scanner.nextInt();

        try {
            stmt = con.prepareStatement("DELETE FROM cliente WHERE id_cliente=" + id);
            stmt.execute();
            System.out.println("Deletado com sucesso!");
        } catch (SQLException e){
            System.out.println("Erro! " + e.getMessage());
        }
    }


    public void alterar_cliente() {
        Connection con = DriverMySQL.getConnection();

        listar_clientes();

        System.out.println("Qual cliente deseja alterar? (id) ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Quais informações deseja alterar? ");
        System.out.println("1 - Nome");
        System.out.println("2 - CPF");
        System.out.println("3 - Endereco");
        System.out.println("4 - Telefone");
        System.out.println("5 - Todas as informações");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (opcao) {
                case 1:
                    System.out.println("Digite o novo nome: ");
                    String novo_nome = scanner.nextLine();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE cliente SET nome_cliente = ? WHERE id_cliente = ?")) {
                        stmt.setString(1, novo_nome);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;

                case 2:
                    System.out.println("Digite o novo CPF: ");
                    String cpf_novo = scanner.nextLine();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE cliente SET cpf = ? WHERE id_cliente = ?")) {
                        stmt.setString(1, cpf_novo);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;

                case 3:
                    int novo_endereco = 0;
                    while (novo_endereco == 0) {
                        enderecoDAO.exibir_endereco();
                        System.out.println("Digite 0 (zero) para adicionar um novo endereço\nDigite o novo endereço do Cliente: ");
                        novo_endereco = scanner.nextInt();
                        scanner.nextLine();
                        if (novo_endereco == 0) {
                            enderecoDAO.inserir_endereco();
                        }
                    }
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE cliente SET id_endereco = ? WHERE id_cliente = ?")) {
                        stmt.setInt(1, novo_endereco);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    }
                    break;

                case 4:
                    System.out.println("Digite a novo telefone: ");
                    String cidade_novo = scanner.nextLine();
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE cliente SET telefone = ? WHERE id_cliente = ?")) {
                        stmt.setString(1, cidade_novo);
                        stmt.setInt(2, id);  // Supondo que o id aqui seja o id_endereco
                        stmt.executeUpdate();
                    }
                    break;

                case 5:
                    System.out.println("Digite o novo nome: ");
                    String nome_novo = scanner.nextLine();

                    System.out.println("Digite o novo CPF: ");
                    String cpf_alterado = scanner.nextLine();

                    int endereco_novo = 0;
                    while (endereco_novo == 0) {
                        enderecoDAO.exibir_endereco();
                        System.out.println("Digite 0 (zero) para adicionar um novo endereço\nDigite o novo endereço do Cliente: ");
                        endereco_novo = scanner.nextInt();
                        scanner.nextLine();
                        if (endereco_novo == 0) {
                            enderecoDAO.inserir_endereco();
                        }
                    }

                    System.out.println("Digite o novo telefone: ");
                    String telefone_alterado = scanner.nextLine();

                    try (PreparedStatement stmt = con.prepareStatement("UPDATE cliente SET nome_cliente = ?, cpf = ?, id_endereco = ?, telefone = ? WHERE id_cliente = ?")) {
                        stmt.setString(1, nome_novo);
                        stmt.setString(2, cpf_alterado);
                        stmt.setInt(3, endereco_novo);
                        stmt.setString(4, telefone_alterado);
                        stmt.setInt(5, id);
                        stmt.executeUpdate();
                    }
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Erro! " + e.getMessage());
        }
    }



}
