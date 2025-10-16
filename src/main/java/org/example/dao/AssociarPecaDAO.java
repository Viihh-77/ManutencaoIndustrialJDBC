package org.example.dao;

import org.example.model.OrdemPeca;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssociarPecaDAO {

    public void associarPecaOrdem (OrdemPeca ordemPeca) throws SQLException {
        String query = """
                INSERT INTO OrdemPeca
                (idOrdem, idPeca, quantidade)
                VALUES
                (?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ordemPeca.getIdOrdem());
            stmt.setInt(2, ordemPeca.getIdPeca());
            stmt.setDouble(3, ordemPeca.getQuantidade());
            stmt.executeUpdate();
        }
    }

}
