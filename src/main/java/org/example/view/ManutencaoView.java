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
        System.out.println("-- Nome: ");
        String nome = input.nextLine();

        System.out.println("-- Setor: ");
        String setor = input.nextLine();

        if (!nome.isEmpty() && !setor.isEmpty()) {
            var maquina = new Maquina(nome, setor, "OPERACIONAL");
            var maquinaDAO = new MaquinaDAO();

            try {
                if (!maquinaDAO.verificaDuplicacao(maquina)) {
                    maquinaDAO.cadastrarMaquina(maquina);
                    System.out.println("Sucesso: Maquina cadastrada!");
                } else {
                    System.out.println("Erro: Maquina já cadastrada!");
                }

            } catch (SQLException e) {
                System.out.println("Erro: Maquina não cadastrada no Banco de Dados!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro: MAQUINA ou SETOR não podem ser nulos!");
        }
    }

    public static void cadastroTecnico() {

        System.out.println("Nome: ");
        String nome = input.nextLine();

        System.out.println("Especialidade: ");
        String especialidade = input.nextLine();

        if (!nome.isEmpty() && !especialidade.isEmpty()) {
            var tecnico = new Tecnico(nome, especialidade);
            var tecnicoDAO = new TecnicoDAO();

            try {
                if (!tecnicoDAO.verificaDuplicidade(tecnico.getNome())) {
                    tecnicoDAO.cadastrarTecnico(tecnico);
                    System.out.println("Sucesso: Tecnico cadastrado!");
                } else {
                    System.out.println("Erro: Tecnico já cadastrado!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro: Tecnico não cadastrado no Bando de Dados!");
            }
        } else {
            System.out.println("Erro: NOME ou ESPECIALIDADE não podem ser nulos!");
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
