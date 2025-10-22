package org.example.dao;

import org.example.database.Conexao;
import org.example.model.NotaEntradaItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotaEntradaItemDAO {

    public void registrarNotaItem (NotaEntradaItem item) throws SQLException{
        String query = "INSERT INTO NotaEntradaItem (idNotaEntrada, idMaterial, quantidade) VALUES (?, ?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, item.getIdNotaEntrada());
            stmt.setInt(2, item.getIdMaterial());
            stmt.setInt(3, item.getQuantidade());
            stmt.executeUpdate();

            System.out.println("Nota Item dessa Nota Entrada registrada com sucesso!");
        }
    }
}
