package org.example.dao;

import org.example.model.Maquina;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Maquina> listarMaquinasOperacional() throws SQLException{
        List<Maquina> maquinas = new ArrayList<>();
        String query = """
                SELECT id
                ,nome
                ,setor
                ,status
                FROM Maquina
                WHERE status = 'OPERACIONAL'
                """;

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String setor = rs.getString("setor");
                String status = rs.getString("status");

                var maquina = new Maquina(id,nome,setor,status);
                maquinas.add(maquina);
            }
        }

        return maquinas;
        
    }

    public void atualizarStatusManutencao(int idMaquina, String status, Connection conn) throws SQLException{
        String query = """
                UPDATE Maquina
                SET status = ?
                WHERE id = ?
                """;

        try(PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1,status);
            stmt.setInt(2,idMaquina);
            stmt.executeUpdate();
        }
    }

}
