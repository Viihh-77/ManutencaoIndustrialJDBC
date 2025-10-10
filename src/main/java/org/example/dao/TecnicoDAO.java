package org.example.dao;

import org.example.model.Tecnico;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TecnicoDAO {

    public void cadastrarTecnico(Tecnico tecnico) throws SQLException {
        String query = """
                INSERT INTO Tecnico
                (nome, especialidade)
                VALUES
                (?,?)
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, tecnico.getNome());
            stmt.setString(2, tecnico.getEspecialidade());
            stmt.executeUpdate();
        }
    }

    public boolean verificaDuplicidade(String nome) throws SQLException {
        String query = """
                SELECT COUNT(0) AS linhas
                FROM Tecnico
                WHERE nome = ?
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt("linhas") > 0) {
                return true;
            }
        }

        return false;

    }
}
