package com.mycompany.rpg.java.entidades.personagens;

import com.mycompany.rpg.java.entidades.inimigos.Inimigo;

public class Mago extends Personagem {

    public Mago(String nome) {
        super(nome, 150, 160);
    }

    // Sobrescreve o Bater para ter a mensagem própria do mago
    @Override
    public void Bater(Inimigo alvo) {
        if (getVida() > 0 && alvo.getVida() > 0) {
            System.out.println(getNome() + " lançou um feitiço em " + alvo.getNome() + "!");
            alvo.TomarPorrada(getDanoTotal());
        }
    }
}//Fim da Classe