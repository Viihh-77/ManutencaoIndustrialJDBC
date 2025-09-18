package org.example.dao;

import org.example.model.Peca;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PecaDAO {

    public boolean cadastroPeca(Peca peca) throws SQLException {

        if (peca.getNome() == null || peca.getNome().isEmpty() || peca.getEstoque() < 0.0) {
            System.out.println("Erro: Nome é obrigatório e/ou o Estoque não pode ser negativo!!");
            return false;
        }

        if (existePeca(peca.getNome())) {
            System.out.println("Erro: Essa peça já está cadastrada!");
            return false;
        }

        String query = """
                INSERT INTO Peca
                (nome,estoque)
                VALUES
                (?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getEstoque());
            int linhas = stmt.executeUpdate();

            return linhas > 0;
        }
    }

    private boolean existePeca(String nome) throws SQLException {

        String query = """
                SELECT COUNT(*) FROM Peca WHERE nome = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;

            }
        }

        return false;

    }
}
