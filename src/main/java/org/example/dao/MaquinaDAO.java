package org.example.dao;

import org.example.model.Maquina;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaquinaDAO {

    public boolean cadastrarMaquina(Maquina maquina) throws SQLException {

        if (maquina.getNome() == null || maquina.getNome().isEmpty() ||
            maquina.getSetor() == null || maquina.getSetor().isEmpty()) {
            System.out.println("Erro: Nome e Setor são obrigatórios!");
            return false;
        }

        if (existeMaquina(maquina.getNome(), maquina.getSetor())) {
            System.out.println("Erro: Já existe uma máquina com esse nome!");
            return false;
        }

        if (maquina.getStatus() == null || maquina.getStatus().isEmpty()) {
            maquina.setStatus("OPERACIONAL");
        }

        String query = """
                INSERT INTO Maquina
                (nome,setor,status)
                VALUES
                (?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            stmt.setString(3, maquina.getStatus());
            int linhas = stmt.executeUpdate();

            return  linhas > 0;
        }
    }

    private boolean existeMaquina(String nome, String setor) throws SQLException {

        String query = """
                SELECT COUNT(*) FROM Maquina WHERE nome = ? AND setor = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nome);
            stmt.setString(2, setor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;

    }
}
