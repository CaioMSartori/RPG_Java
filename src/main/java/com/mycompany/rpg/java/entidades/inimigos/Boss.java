
package com.mycompany.rpg.java.entidades.inimigos;

import com.mycompany.rpg.java.entidades.personagens.Personagem;
import java.util.Random;

public class Boss extends Inimigo {

    private final int defesa;
    private final String tipoBoss;
    private final String descricaoEspecial;

    // Construtor
    public Boss(
            String nome,
            int vida,
            int dano,
            int defesa,
            String tipoBoss,
            String descricaoEspecial) {

        super(nome, vida, dano);

        this.defesa = defesa;
        this.tipoBoss = tipoBoss;
        this.descricaoEspecial = descricaoEspecial;
    }

    // Getters
    public String getTipoBoss() {
        return tipoBoss;
    }

    public int getDefesa() {
        return defesa;
    }

    // Aplica a defesa do Boss ao dano recebido
    @Override
    public void TomarPorrada(int dano) {

        int danoReal = Math.max(1, dano - defesa);

        super.TomarPorrada(danoReal);
    }

    // Ataque especial do Boss
    @Override
    public void Bater(Personagem alvo) {

        if (getVida() > 0 && alvo.getVida() > 0) {

            System.out.println(
                    getNome() + " " + descricaoEspecial);

            alvo.TomarPorrada(getDano());
        }
    }

    // Sorteia um dos sete chefes elementais
    public static Boss sortearBoss() {

        return switch (new Random().nextInt(7)) {

            case 0 -> new Boss(
                    "Dragão de Fogo",
                    150,
                    25,
                    5,
                    "Fogo",
                    "lança uma explosão de chamas!"
            );

            case 1 -> new Boss(
                    "Golem de Gelo",
                    200,
                    15,
                    15,
                    "Gelo",
                    "congela o terreno!"
            );

            case 2 -> new Boss(
                    "Titã Elétrico",
                    120,
                    30,
                    2,
                    "Elétrico",
                    "descarrega um raio poderoso!"
            );

            case 3 -> new Boss(
                    "Cobra Venenosa",
                    100,
                    20,
                    8,
                    "Veneno",
                    "libera uma nuvem de gás tóxico!"
            );

            case 4 -> new Boss(
                    "Golem de Terra",
                    250,
                    10,
                    20,
                    "Terra",
                    "causa um terremoto!"
            );

            case 5 -> new Boss(
                    "Fênix do Vento",
                    110,
                    28,
                    3,
                    "Ar",
                    "cria uma tempestade de lâminas!"
            );

            default -> new Boss(
                    "Sombra Eterna",
                    180,
                    22,
                    10,
                    "Escuro",
                    "drena a alma do jogador!"
            );
        };
    }
}
