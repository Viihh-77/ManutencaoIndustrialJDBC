package org.example.view;

import java.sql.SQLException;
import java.util.Scanner;

import org.example.dao.MaquinaDAO;
import org.example.dao.PecaDAO;
import org.example.dao.TecnicoDAO;
import org.example.model.Maquina;
import org.example.model.Peca;
import org.example.model.Tecnico;

public class ManutencaoView {

    static Scanner input = new Scanner(System.in);

    public static void cadastroMaquina() {
        try {
            var maquinaDAO = new MaquinaDAO();

            System.out.println("-- Nome: ");
            String nome = input.nextLine();

            System.out.println("-- Setor: ");
            String setor = input.nextLine();

            Maquina maquina = new Maquina(nome,setor);
            boolean sucesso = maquinaDAO.cadastrarMaquina(maquina);

            if (sucesso) {
                System.out.println("Sucesso: Máquina cadastrada!");
            } else {
                System.out.println("Erro: Máquina não cadastrada!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Máquina não cadastrada no banco de dados!");
        }
    }

    public static void cadastroTecnico() {
        try {
            var tecnicoDAO = new TecnicoDAO();

            System.out.println("-- Nome: ");
            String nome = input.nextLine();

            System.out.println("-- Especialidade: ");
            String especialidade = input.nextLine();

            Tecnico tecnico = new Tecnico(nome, especialidade);
            boolean sucesso = tecnicoDAO.cadastroTecnico(tecnico);

            if (sucesso) {
                System.out.println("Sucesso: Técnico cadastrado!");
            } else {
                System.out.println("Erro: Técnico não cadastrado!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Técnico não cadastrado no banco de dados!");
        }
    }

    public static void cadastroPeca() {
        try {
            var pecaDAO = new PecaDAO();

            System.out.println("-- Nome: ");
            String nome = input.nextLine();

            System.out.println("-- Estoque: ");
            double estoque = input.nextDouble();

            Peca peca = new Peca(nome, estoque);
            boolean sucesso = pecaDAO.cadastroPeca(peca);

            if (sucesso) {
                System.out.println("Sucesso: Peça cadastrada!");
            } else {
                System.out.println("Erro: Peça não cadastrada!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Peça não cadastrada no banco de dados!");
        }
    }

}
