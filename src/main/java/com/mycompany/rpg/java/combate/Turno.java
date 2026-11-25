
package com.mycompany.rpg.java.combate;

import com.mycompany.rpg.java.entidades.personagens.Personagem;
import com.mycompany.rpg.java.entidades.inimigos.Inimigo;
import com.mycompany.rpg.java.itens.Item;
import com.mycompany.rpg.java.itens.Pc;
import com.mycompany.rpg.java.itens.Pd;

import java.util.List;
import java.util.Scanner;

public class Turno {

    private final Scanner scanner;

    public Turno(Scanner scanner) {

        this.scanner = scanner;
    }

    // Executa o turno do jogador
    public boolean executarTurnoJogador(
            Personagem jogador, List<Inimigo> inimigos) {

        while (true) {

            System.out.println("\n1. Atacar");
            System.out.println("2. Usar poção de cura");
            System.out.println("3. Usar poção de dano");

            int opcao = lerOpcao(3);

            if (opcao == -1) {

                return false;
            }

            switch (opcao) {

                case 1 -> {

                    Inimigo alvo = escolherAlvo(inimigos);

                    if (alvo == null) {

                        return false;
                    }

                    jogador.Bater(alvo);

                    return true;
                }

                case 2 -> {

                    Item pocao = buscarPocao(
                            jogador, Pc.class);

                    if (pocao == null) {

                        System.out.println(
                                "Você não possui poções de cura.");

                    } else if (jogador.getVida()
                            == jogador.getVidaMaxima()) {

                        System.out.println(
                                "Sua vida já está cheia.");

                    } else if (jogador.usar(pocao)) {

                        return true;
                    }
                }

                case 3 -> {

                    Item pocao = buscarPocao(
                            jogador, Pd.class);

                    if (pocao == null) {

                        System.out.println(
                                "Você não possui poções de dano.");

                    } else {

                        Inimigo alvo = escolherAlvo(inimigos);

                        if (alvo == null) {

                            return false;
                        }

                        if (jogador.usar(pocao, alvo)) {

                            return true;
                        }
                    }
                }
            }
        }
    }

    // Executa o turno de um inimigo
    public void executarTurnoInimigo(
            Inimigo inimigo, Personagem jogador) {

        inimigo.Bater(jogador);
    }

    // Busca uma poção no inventário
    private Item buscarPocao(
            Personagem jogador, Class<? extends Item> tipo) {

        for (Item item : jogador.getInventario().getItens()) {

            if (tipo.isInstance(item)) {

                return item;
            }
        }

        return null;
    }

    // Seleciona um inimigo vivo
    private Inimigo escolherAlvo(List<Inimigo> inimigos) {

        List<Inimigo> vivos = inimigos.stream()
                .filter(i -> i.getVida() > 0)
                .toList();

        if (vivos.isEmpty()) {

            return null;
        }

        if (vivos.size() == 1) {

            return vivos.get(0);
        }

        System.out.println("\nEscolha o alvo:");

        for (int i = 0; i < vivos.size(); i++) {

            System.out.println(
                    (i + 1) + ". "
                    + vivos.get(i).getNome()
                    + " (" + vivos.get(i).getVida() + " HP)");
        }

        int opcao = lerOpcao(vivos.size());

        return opcao == -1 ? null : vivos.get(opcao - 1);
    }

    // Validação da entrada
    private int lerOpcao(int maximo) {

        while (true) {

            System.out.print("Escolha: ");

            if (!scanner.hasNextLine()) {

                return -1;
            }

            String entrada = scanner.nextLine().trim();

            try {

                int opcao = Integer.parseInt(entrada);

                if (opcao >= 1 && opcao <= maximo) {

                    return opcao;
                }

            } catch (NumberFormatException e) {

                // Entrada não numérica.
            }

            System.out.println(
                    "Opção inválida. Tente novamente.");
        }
    }
}
