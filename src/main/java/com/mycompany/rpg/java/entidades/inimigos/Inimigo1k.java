package com.mycompany.rpg.java.entidades.inimigos;

import com.mycompany.rpg.java.entidades.personagens.Personagem;
import java.util.Random;

public class Inimigo1k extends Inimigo {

    private Random sorteio;

    public Inimigo1k(String nome) {
        super(nome, 1000, 60);
        this.sorteio = new Random();
    }

    // Ataque forte: o dobro do dano base
    public void LancaDeGelo(Personagem alvo) {
        if (getVida() > 0 && alvo.getVida() > 0) {
            System.out.println(getNome() + " arremessou uma LANÇA DE GELO em " + alvo.getNome() + "!");
            alvo.TomarPorrada(getDano() * 2);
        }
    }

    // Ataque médio: uma vez e meia o dano base
    public void BolaDeGelo(Personagem alvo) {
        if (getVida() > 0 && alvo.getVida() > 0) {
            System.out.println(getNome() + " lançou uma BOLA DE GELO em " + alvo.getNome() + "!");
            alvo.TomarPorrada(getDano() * 3 / 2);
        }
    }

    // Ataque básico: o dano base
    public void SocoDeGelo(Personagem alvo) {
        if (getVida() > 0 && alvo.getVida() > 0) {
            System.out.println(getNome() + " acertou um SOCO DE GELO em " + alvo.getNome() + "!");
            alvo.TomarPorrada(getDano());
        }
    }

    // Escolhe um dos três ataques aleatoriamente
    public void Atacar(Personagem alvo) {
        int escolha = sorteio.nextInt(3);

        if (escolha == 0) {
            LancaDeGelo(alvo);
        } else if (escolha == 1) {
            BolaDeGelo(alvo);
        } else {
            SocoDeGelo(alvo);
        }
    }
}//Fim da Classe