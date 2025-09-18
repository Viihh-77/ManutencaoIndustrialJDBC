package org.example.dao;

import org.example.model.Tecnico;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TecnicoDAO {

    public boolean cadastroTecnico (Tecnico tecnico) throws SQLException {

        if (tecnico.getNome() == null || tecnico.getNome().isEmpty() ||
            tecnico.getEspecialidade() == null || tecnico.getEspecialidade().isEmpty()) {
            System.out.println("Erro: Nome e Especialidade são obrigatórios!");
            return false;
        }

        if (existeTecnico(tecnico.getNome(), tecnico.getEspecialidade())) {
            System.out.println("Erro: Já existe um cadastro com este nome!");
            return false;
        }

        String query = """
                INSERT INTO Tecnico
                (nome,especialidade)
                VALUES
                (?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tecnico.getNome());
            stmt.setString(2, tecnico.getEspecialidade());
            int linhas = stmt.executeUpdate();

            return linhas > 0;
        }
    }

    private boolean existeTecnico(String nome, String especialidade) throws SQLException {

        String query = """
                SELECT COUNT(*) FROM Tecnico WHERE nome = ? AND  especialidade = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nome);
            stmt.setString(2, especialidade);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;

    }
}
