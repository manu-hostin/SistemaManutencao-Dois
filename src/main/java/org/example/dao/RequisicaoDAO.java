package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Requisicao;

import java.sql.*;
import java.time.LocalDate;

public class RequisicaoDAO {

    public void registrarRequisicao (Requisicao req) throws SQLException{
        String query = "INSERT INTO Requisicao (setor, dataSolicitacao, status) VALUES (?, ?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, req.getSetor());
            stmt.setDate(2, Date.valueOf(req.getDataSolicitacao()));
            stmt.setString(3, req.getStatus());
            stmt.executeUpdate();

            System.out.println("Requisição realizada com sucesso!");
        }
    }
    public int pegarIDRequisicao (String setor, LocalDate data) throws SQLException{
        String query = "SELECT id FROM Requisicao WHERE setor = ? AND dataSolicitacao = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, setor);
            stmt.setDate(2, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            } else {
                return -1;
            }
        }

    }
}
