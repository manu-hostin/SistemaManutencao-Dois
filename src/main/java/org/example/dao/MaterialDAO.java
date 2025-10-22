package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    public void cadastrarMaterial(Material material) throws SQLException{
        String query = "INSERT INTO Material (nome, unidade, estoque) VALUES (?,?,?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, material.getNome());
            stmt.setString(2, material.getUnidade());
            stmt.setInt(3, material.getEstoque());
            stmt.executeUpdate();

            System.out.println("Material inserido com sucesso!");
        }
    }
    public List<Material> listarMateriais () throws SQLException{
        String query = "SELECT id, nome, unidade, estoque FROM Material";
        List<Material> materiais = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String unidade = rs.getString("unidade");
                int estoque = rs.getInt("estoque");

                var material = new Material(id, nome, unidade, estoque);
                materiais.add(material);
            }
        }
        return materiais;
    }
}

