package com.mycompany.rpg.java.entidades.personagens;

import com.mycompany.rpg.java.entidades.inimigos.Inimigo;

public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        super(nome, 180, 130);
    }

    // Sobrescreve o Bater para ter a mensagem própria do arqueiro
    @Override
    public void Bater(Inimigo alvo) {
        if (getVida() > 0 && alvo.getVida() > 0) {
            System.out.println(getNome() + " atirou uma flecha em " + alvo.getNome() + "!");
            alvo.TomarPorrada(getDanoTotal());
        }
    }
}//Fim da Classe