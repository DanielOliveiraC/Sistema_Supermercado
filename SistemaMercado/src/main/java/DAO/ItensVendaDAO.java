package DAO;

import BancoDeDados.DriverMySQL;
import Model.ItensVenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItensVendaDAO {
    private Connection con = DriverMySQL.getConnection();

    public ItensVendaDAO() {}

    public void adicionarProdutoAVenda(ItensVenda itensVenda) throws SQLException {
        String sql = "INSERT INTO itensVenda (id_venda, id_produto, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, itensVenda.getIdVenda());
            stmt.setInt(2, itensVenda.getIdProduto());
            stmt.setInt(3, itensVenda.getQuantidade());
            stmt.setBigDecimal(4, itensVenda.getPrecoUnitario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
