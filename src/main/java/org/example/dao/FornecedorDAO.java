package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Fornecedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FornecedorDAO {

    public void cadastrarFornecedor(Fornecedor forn) throws SQLException{
        String query = "INSERT INTO Fornecedor (nome, cnpj) VALUES (?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, forn.getNome());
            stmt.setString(2, forn.getCnpj());
            stmt.executeUpdate();

            System.out.println("\nFornecedor inserido com sucesso!");
        }
    }
    public int pegarIDForn (String cnpj) throws SQLException{
        String query = "SELECT id FROM Fornecedor WHERE cnpj = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1,cnpj);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                System.out.println("Fornecedor não válido.");
                return -1;
            }
        }
    }
}
