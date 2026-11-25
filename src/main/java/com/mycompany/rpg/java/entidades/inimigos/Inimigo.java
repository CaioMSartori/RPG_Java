
package com.mycompany.rpg.java.entidades.inimigos;

import com.mycompany.rpg.java.entidades.personagens.Personagem;

public class Inimigo {

    private String nome;
    private int vida;
    private int dano;

    // Construtor
    public Inimigo(String nome, int vida, int dano) {

        this.nome = nome;
        this.vida = vida;
        this.dano = dano;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getDano() {
        return dano;
    }

    // Ataca um personagem
    public void Bater(Personagem alvo) {

        if (vida > 0 && alvo.getVida() > 0) {

            System.out.println(
                    nome + " atacou " + alvo.getNome() + "!");

            alvo.TomarPorrada(dano);
        }
    }

    // Recebe dano
    public void TomarPorrada(int dano) {

        vida = vida - dano;

        if (vida <= 0) {

            vida = 0;

            System.out.println(nome + " morreu!");

        } else {

            System.out.println(
                    nome + " possui " + vida + " de vida.");
        }
    }
}
