package org.example.dao;

import org.example.model.Tecnico;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        public List<Tecnico> listarTecnicos() throws SQLException{
        List<Tecnico> tecnicos = new ArrayList<>();
        String query = """
                SELECT id
                ,nome
                ,especialidade
                FROM Tecnico
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String especialidade = rs.getString("especialidade");

                var tecnico = new Tecnico(id,nome,especialidade);
                tecnicos.add(tecnico);
            }
        }

        return tecnicos;
        
    }

}
