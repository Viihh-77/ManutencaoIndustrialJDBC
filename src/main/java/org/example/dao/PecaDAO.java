package org.example.dao;

import org.example.model.Peca;
import org.example.util.Conexao;

import com.mysql.cj.protocol.a.SqlDateValueEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Peca> listarPecas() throws SQLException {
        List<Peca> pecas = new ArrayList<>();

        String query = """
                SELECT id, nome, estoque
                FROM Peca
                WHERE 1=1;
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double estoque = rs.getDouble("estoque");

                var peca = new Peca(id,nome,estoque);
                pecas.add(peca);

            }
        }

        return pecas;

    }
    
}

