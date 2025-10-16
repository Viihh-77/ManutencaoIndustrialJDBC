package org.example.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.dao.*;
import org.example.model.*;
import org.example.util.Conexao;

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
        
        System.out.println("Nome: ");
        String nome = input.nextLine();

        System.out.println("Estoque: ");
        double estoque = input.nextDouble();
        input.nextLine();

        if (!nome.isEmpty() && estoque >= 0) {
            var peca = new Peca(nome, estoque);
            var pecaDAO = new PecaDAO();

            try {
                if (!pecaDAO.verificaDuplicidade(peca.getNome())) {
                    pecaDAO.cadastroPeca(peca);
                    System.out.println("Sucesso: Peça cadastrada!");
                } else {
                    System.out.println("Erro: Peça já cadastrada!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro: Peça não cadastrada no Banco de Dados!");
            }

        } else {
            System.out.println("Erro: NOME não pode ser nulo e ESTOQUE não pode ser negativo!");
        }
    }

    public static void cadastrarOrdemManutencao() {

        var maquinaDAO = new MaquinaDAO();
        List<Maquina> maquinas = new ArrayList<>();
        List<Tecnico> tecnicos = new ArrayList<>();
        List<Integer> opcoesMaquinas = new ArrayList<>();
        List<Integer> opcoesTecnicos = new ArrayList<>();

        try {
            maquinas = maquinaDAO.listarMaquinasOperacional();
        } catch (SQLException e) {
            System.out.println("Erro: Não foi possível carregar as máquinas do Banco de Dados!");
            e.printStackTrace();
        }

        maquinas.forEach(maquina -> {
            System.out.println("ID Maquina: " + maquina.getId());
            System.out.println("Nome Maquina: " + maquina.getNome());
            System.out.println("Setor Maquina: " + maquina.getSetor());
            System.out.println("Status Maquina: " + maquina.getStatus());
            System.out.println(" ");

            opcoesMaquinas.add(maquina.getId());
        });

        System.out.println("ID Maquina: ");
        int idMaquina = input.nextInt();
        input.nextLine();
        System.out.println(" ");

        var tecnicoDAO = new TecnicoDAO();

        if (opcoesMaquinas.contains(idMaquina)) {

            try {
                tecnicos = tecnicoDAO.listarTecnicos();
            } catch (SQLException e) {
                System.out.println("Erro: Não foi possível carregar os técnicos do Banco de Dados!");
                e.printStackTrace();
            }

            tecnicos.forEach(tecnico -> {
                System.out.println("ID Técnico: " + tecnico.getId());
                System.out.println("Nome Técnico: " + tecnico.getNome());
                System.out.println("Especialidade Técnico: " + tecnico.getEspecialidade());
                System.out.println(" ");

                opcoesTecnicos.add(tecnico.getId());
            });

            System.out.println(" ");
            System.out.println("ID Tecnico: ");
            int idTecnico = input.nextInt();
            input.nextLine();

            var ordemManutencaoDAO = new OrdemManutencaoDAO();

            if (opcoesTecnicos.contains(idTecnico)) {
                var ordemManutencao = new OrdemManutencao(idMaquina, idTecnico, LocalDate.now(), "PENDENTE");
                Connection conn = null;

                try {
                    conn = Conexao.conectar();
                    conn.setAutoCommit(false);

                    ordemManutencaoDAO.cadastrarOrdemManutencao(ordemManutencao, conn);
                    maquinaDAO.atualizarStatusManutencao(idMaquina, "EM_MANUTENÇÃO", conn);

                    conn.commit();
                    System.out.println("Sucesso: Ordem de Manutenção cadastrada!");
                
                } catch (SQLException e) {
                    try {
                        conn.rollback();
                        conn.close();
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                    System.out.println("Erro: Ordem de Manutenção não cadastrada no Banco de Dados!");
                } finally {
                    try{
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                System.out.println("Erro: Opção inválida!");
            }

        } else {
            System.out.println("Erro: ID inválido!");
        }
    }

    public static void associarPecasOrdem() {
        boolean sair = false;
        List<OrdemManutencaoPeca> ordemManutencaoPecas = new ArrayList<>();
        List<Integer> opcoesOrdem = new ArrayList<>();

        var ordemManutencaoDAO = new OrdemManutencaoDAO();

        try {
            ordemManutencaoPecas = ordemManutencaoDAO.listarOrdens();
        } catch (SQLException e) {
            System.out.println("Erro: não foi possivel fazer a busca no banco de dados!");
            e.printStackTrace();
        }

        System.out.println("Ordens Pendentes: ");
        ordemManutencaoPecas.forEach(ordem -> {

            System.out.println("ID ordem: " + ordem.getId());
            System.out.println("ID maquina: " + ordem.getIdMaquina());
            System.out.println("Nome máquina: " + ordem.getNomeMaquina());
            System.out.println("ID técnico: " + ordem.getIdTecnico());
            System.out.println("Nome técnico: " + ordem.getNomeTecnico());
            System.out.println("Status: " + ordem.getStatus());
            System.out.println("Data solicitação: " + ordem.getDataSolicitacao());
            System.out.println(" ");

            opcoesOrdem.add(ordem.getId());
        });

        System.out.println("ID ordem: ");
        int idOrdem = input.nextInt();
        input.nextLine();

        var pecaDAO = new PecaDAO();
        List<Peca> pecas = new ArrayList<>();

        if (opcoesOrdem.contains(idOrdem)) {
            try {
                pecas = pecaDAO.listarPecas();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro: não foi possivel conectar ao banco de dados!");
            }

            List<Integer> opcoesPecas = new ArrayList<>();
            System.out.println("Lista de Peças: ");

            pecas.forEach(peca -> {
                System.out.println("ID: " + peca.getId());
                System.out.println("Nome: " + peca.getNome());
                System.out.println("Estoque: " + peca.getEstoque());

                opcoesPecas.add(peca.getId());
            });

            System.out.println("ID peça: ");
            int idPeca = input.nextInt();
            input.nextLine();

            if (opcoesPecas.contains(idPeca)) {
                System.out.println("Quantidade: ");
                double quantidade = input.nextDouble();
                input.nextLine();

                int indicePeca = opcoesPecas.indexOf(idPeca);
                Peca pecaEscolhida = pecas.get(indicePeca);

                if (pecaEscolhida.getEstoque() >= quantidade) {
                    var associarPecaDAO = new AssociarPecaDAO();

                    try {
                        associarPecaDAO.associarPecaOrdem(new OrdemPeca(idOrdem, idPeca,quantidade));
                        pecas.remove(indicePeca);
                        opcoesPecas.remove(indicePeca);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Erro: não foi possivel se conectar ao banco de dados!");
                    }
                } else {
                    System.out.println("Erro: estoque insuficiente!");
                    return;
                }
            } else {
                System.out.println("Erro: ID da peça inválido!");
            }

        } else {
            System.out.println("Erro: opção inválida!");
        }

    }

    public static void executarManutencao() {

    }
}
