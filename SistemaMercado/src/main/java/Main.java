import DAO.*;
import Model.*;
import BancoDeDados.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        // Inicializando as classes
        Connection con = DriverMySQL.getConnection();
        PreparedStatement ps = null;

        Scanner scanner = new Scanner(System.in);

        Produtos produtos = new Produtos();
        ProdutosDAO produtosDAO = new ProdutosDAO();

        Endereco endereco = new Endereco();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        Cliente cliente = new Cliente();
        ClientesDAO clientesDAO = new ClientesDAO();

        Venda venda = new Venda();
        VendaDAO vendaDAO = new VendaDAO();

        ItensVenda itensVenda = new ItensVenda();
        ItensVendaDAO itensVendaDAO = new ItensVendaDAO();


        int opcao;

        do {
            System.out.println("=================================");
            System.out.println("    BEM-VINDO AO NEXTGENMARKET     ");
            System.out.println("=================================");
            System.out.println("1.  Inserir Cliente");
            System.out.println("2.  Listar Clientes");
            System.out.println("3.  Excluir Cliente");
            System.out.println("4.  Alterar Cliente");
            System.out.println("---------------------------------");
            System.out.println("5.  Inserir Produto");
            System.out.println("6.  Listar Produtos");
            System.out.println("7.  Excluir Produto");
            System.out.println("8.  Alterar Produto");
            System.out.println("---------------------------------");
            System.out.println("9.  Inserir Endereço");
            System.out.println("10. Exibir Endereço");
            System.out.println("11. Alterar Endereço");
            System.out.println("12. Excluir Endereço");
            System.out.println("---------------------------------");
            System.out.println("13. Realizar Venda");
            System.out.println("14. Consultar Venda");
            System.out.println("---------------------------------");
            System.out.println("0.  Sair");
            System.out.println("=================================");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    clientesDAO.inserir_cliente();
                    break;
                case 2:
                    clientesDAO.listar_clientes();
                    break;
                case 3:
                    clientesDAO.excluir_cliente();
                    break;
                case 4:
                    clientesDAO.alterar_cliente();
                    break;
                case 5:
                    produtosDAO.inserir_produto();
                    break;
                case 6:
                    produtosDAO.listar_produtos();
                    break;
                case 7:
                    produtosDAO.excluir_produto();
                    break;
                case 8:
                    produtosDAO.alterar_produto();
                    break;
                case 9:
                    enderecoDAO.inserir_endereco();
                    break;
                case 10:
                    enderecoDAO.exibir_endereco();
                    break;
                case 11:
                    enderecoDAO.alterar_endereco();
                    break;
                case 12:
                    enderecoDAO.excluir_endereco();
                    break;
                case 13:
                    vendaDAO.realizarVenda();
                    break;
                case 14:
                    clientesDAO.listar_clientes();
                    System.out.println("Qual id do cliente que deseja verificar a compra? ");
                    int id = scanner.nextInt();
                    vendaDAO.exibirVendaCliente(id);
                case 0:
                    System.out.println("Saindo... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();

    }
}
