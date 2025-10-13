package org.example.dao;

import org.example.model.Peca;
import org.example.util.Conexao;

import com.mysql.cj.protocol.a.SqlDateValueEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PecaDAO {

    public void cadastroPeca(Peca peca) throws SQLException {
        String query = """
                INSERT INTO Peca
                (nome, estoque)
                VALUES
                (?, ?)
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, peca.getNome());
                stmt.setDouble(2, peca.getEstoque());
                stmt.executeUpdate();
        }
    }

    public boolean verificaDuplicidade(String nome) throws SQLException {
        String query = """
                SELECT COUNT(*) AS linhas
                FROM Peca
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

