
package com.mycompany.rpg.java.missoes;

import com.mycompany.rpg.java.entidades.personagens.Personagem;
import java.util.Random;

public class Recompensa {

    private final Random random = new Random();

    // Sorteia e entrega a recompensa ao jogador
    public void darRecompensa(Personagem jogador) {

        // Sorteia entre 50 e 99 moedas
        int moedasGanhas = random.nextInt(50) + 50;

        jogador.adicionarMoedas(moedasGanhas);

        System.out.println("\nRECOMPENSA DA VITÓRIA:");

        System.out.println(
                "+ " + moedasGanhas + " moedas!");

        System.out.println(
                "Total de moedas: " + jogador.getMoedas());
    }
}
