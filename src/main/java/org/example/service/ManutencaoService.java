package org.example.service;

import org.example.view.ManutencaoView;

import java.util.Scanner;

public class ManutencaoService {

    static Scanner input = new Scanner(System.in);

    public static void menuInicial() {
        boolean sair = false;

        while (!sair) {

            System.out.println(" ");
            System.out.println(" -------------------------------------- ");
            System.out.println("|   SISTEMA DE MANUTENÇÃO INDUSTRIAL   |");
            System.out.println("|                                      |");
            System.out.println("| 1 - Cadastrar Máquina                |");
            System.out.println("| 2 - Cadastrar Técnico                |");
            System.out.println("| 3 - Cadastrar Peça                   |");
            System.out.println("| 4 - Criar Ordem de Manutenção        |");
            System.out.println("| 5 - Associar Peçaas à Ordem          |");
            System.out.println("| 6 - Executar Manutenção              |");
            System.out.println("|                                      |");
            System.out.println("| 0 - Sair                             |");
            System.out.println(" -------------------------------------- ");
            System.out.println(" ");
            int opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {

                case 1 -> {

                    System.out.println(" ");
                    System.out.println(" ---------------------- ");
                    System.out.println("|   CADASTRO MÁQUINA   |");
                    System.out.println(" ---------------------- ");
                    System.out.println(" ");

                    ManutencaoView.cadastroMaquina();
                }

                case 2 -> {

                    System.out.println(" ");
                    System.out.println(" ---------------------- ");
                    System.out.println("|   CADASTRO TÉCNICO   |");
                    System.out.println(" ---------------------- ");
                    System.out.println(" ");

                    ManutencaoView.cadastroTecnico();
                }

                case 3 -> {

                }

                case 4 -> {

                }

                case 5 -> {

                }

                case 6 -> {

                }

                case 0 -> {
                    System.out.println(" ");
                    System.out.println("Encerrando Sistema...");
                    sair = true;
                }

                default -> System.out.println("Erro: Digite um número válido!");
            }
        }
    }
}
