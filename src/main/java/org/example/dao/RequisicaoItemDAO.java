package org.example.dao;

import org.example.database.Conexao;
import org.example.model.RequisicaoItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequisicaoItemDAO {

    public void registrarReqItem (RequisicaoItem req) throws SQLException{
        String query = "INSERT INTO RequisicaoItem (idRequisicao, idMaterial, quantidade) VALUES (?, ?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, req.getIdRequisicao());
            stmt.setInt(2, req.getIdMaterial());
            stmt.setDouble(3, req.getQuantidade());
            stmt.executeUpdate();

            System.out.println("Item Requisição registrado com sucesso!");
        }
    }
}
