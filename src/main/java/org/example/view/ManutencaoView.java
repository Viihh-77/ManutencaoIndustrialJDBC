package org.example.view;

import java.sql.SQLException;
import java.util.Scanner;

import org.example.dao.MaquinaDAO;
import org.example.model.Maquina;

public class ManutencaoView {

    static Scanner input = new Scanner(System.in);

    public  static void cadastroMaquina() {
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
            System.out.println("Erro: Máquina não cadastrada no banco!");
        }
    }

}
