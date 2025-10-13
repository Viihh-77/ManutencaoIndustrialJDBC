package org.example.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.model.OrdemManutencao;
import org.example.model.OrdemManutencaoPeca;
import org.example.util.Conexao;

public class OrdemManutencaoDAO {

    public void cadastrarOrdemManutencao(OrdemManutencao ordem, Connection conn) throws SQLException {
        String query = """
                INSERT INTO OrdemManutencao 
                (id_maquina
                ,id_tecnico
                ,dataSolicitacao
                ,status)
                VALUES
                (?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, ordem.getIdMaquina());
                stmt.setInt(2, ordem.getIdTecnico());
                stmt.setDate(3, Date.valueOf(ordem.getDataSolicitacao()));
                stmt.setString(4, ordem.getStatus());
                stmt.executeUpdate();
            }
        }

    public List<OrdemManutencao> listarOrdens() throws SQLException {
        List<OrdemManutencao> ordens = new ArrayList<>();
        String query = """
                SELECT 0.id
                , O.id_maquina
                , M.nome AS nomeMaquina
                , O.id_tecnico
                , T.nome AS nomeTecnico
                , 0.status
                , 0.dataSolicitacao
                FROM OrdemManutencao O
                JOIN Maquina M ON O.id_maquina = M.id
                JOIN Tecnico T ON O.id_tecnico = T.id
                WHERE O.status = 'PENDENTE'
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idMaquina = rs.getInt("idMaquina");
                String nomeMaquina = rs.getString("nomeMaquina");
                int idTecnico = rs.getInt("idTecnico");
                String nomeTecnico = rs.getString("nomeTecnico");
                String status = rs.getString("status");
                LocalDate dataSolicitacao = rs.getDate("dataSolicitacao").toLocalDate();
            
                var OrdemManutencaoPeca = new OrdemManutencaoPeca(
                    id,
                    idMaquina,
                    idTecnico,
                    dataSolicitacao,
                    status,
                    nomeMaquina,
                    nomeTecnico
                );

                ordens.add(OrdemManutencaoPeca);

            }
        }

        return ordens;
        
    }

    }
