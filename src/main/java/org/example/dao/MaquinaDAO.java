package org.example.dao;

import org.example.model.Maquina;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaquinaDAO {

    public void cadastrarMaquina(Maquina maquina) throws SQLException {
        String query = """
                INSERT INTO Maquina
                (nome, setor, status)
                VALUES
                (?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            stmt.setString(3, maquina.getStatus());
            stmt.executeUpdate();
        }
    }

    public boolean verificaDuplicacao(Maquina maquina) throws SQLException {
        String query = """
                SELECT COUNT(0) AS linhas
                FROM Maquina
                WHERE nome = ?
                AND setor = ?
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt("linhas") > 0) {
                return  true;
            }
        }

        return false;

    }
}
