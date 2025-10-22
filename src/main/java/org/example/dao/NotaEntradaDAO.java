package org.example.dao;

import org.example.database.Conexao;
import org.example.model.NotaEntrada;

import java.sql.*;
import java.time.LocalDate;

public class NotaEntradaDAO {

    public void registrarNotaEntrada (NotaEntrada nota) throws SQLException{
        String query = "INSERT INTO NotaEntrada (idFornecedor, dataEntrada) VALUES (?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, nota.getIdFornecedor());
            stmt.setDate(2, Date.valueOf(nota.getDataEntrada()));
            stmt.executeUpdate();

            System.out.println("Nota de entrada cadastrada com sucesso!");
        }
    }
    public int pegarIDNota (int idForn, LocalDate data) throws SQLException{
        String query = "SELECT id FROM NotaEntrada WHERE idFornecedor = ? AND dataEntrada = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idForn);
            stmt.setDate(2, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        }

    }
}
