
package com.mycompany.rpg.java.combate;

import com.mycompany.rpg.java.entidades.personagens.Personagem;
import com.mycompany.rpg.java.entidades.inimigos.Inimigo;
import com.mycompany.rpg.java.missoes.Recompensa;
import java.util.List;
import java.util.Scanner;

public class Batalha {

    private final Turno turnoController;
    private final Recompensa recompensaController;

    public Batalha(Scanner scanner) {
        turnoController = new Turno(scanner);
        recompensaController = new Recompensa();
    }

    // Batalha contra um único inimigo
    public boolean iniciarBatalha(
            Personagem jogador, Inimigo inimigo) {

        return iniciarBatalha(jogador, List.of(inimigo));
    }

    // Batalha contra múltiplos inimigos
    public boolean iniciarBatalha(
            Personagem jogador, List<Inimigo> inimigos) {

        List<Inimigo> participantes = List.copyOf(inimigos);

        // Validação dos participantes
        if (jogador.getVida() <= 0
                || participantes.isEmpty()
                || participantes.stream().anyMatch(i -> i.getVida() <= 0)
                || participantes.stream().distinct().count() != participantes.size()) {

            throw new IllegalArgumentException(
                    "Participantes inválidos para a batalha.");
        }

        int numeroTurno = 1;

        while (jogador.getVida() > 0
                && existemVivos(participantes)) {

            System.out.println("\n=== TURNO " + numeroTurno++ + " ===");

            // Exibe o status do jogador
            System.out.println(
                    jogador.getNome() + ": "
                    + jogador.getVida() + "/"
                    + jogador.getVidaMaxima() + " HP");

            // Exibe o status dos inimigos
            for (Inimigo inimigo : participantes) {
                System.out.println(
                        inimigo.getNome() + ": "
                        + inimigo.getVida() + " HP");
            }

            // Turno do jogador
            if (!turnoController.executarTurnoJogador(
                    jogador, participantes)) {

                System.out.println("Batalha interrompida.");
                return false;
            }

            // Turno dos inimigos vivos
            for (Inimigo inimigo : participantes) {

                if (jogador.getVida() <= 0) {
                    break;
                }

                if (inimigo.getVida() > 0) {
                    turnoController.executarTurnoInimigo(
                            inimigo, jogador);
                }
            }
        }

        // Verifica o resultado da batalha
        if (jogador.getVida() > 0) {
            System.out.println("\nVitória!");

            recompensaController.darRecompensa(jogador);

            return true;
        }

        System.out.println("\nDerrota! Game Over.");
        return false;
    }

    // Verifica se ainda existem inimigos vivos
    private boolean existemVivos(List<Inimigo> inimigos) {
        return inimigos.stream().anyMatch(i -> i.getVida() > 0);
    }
}
