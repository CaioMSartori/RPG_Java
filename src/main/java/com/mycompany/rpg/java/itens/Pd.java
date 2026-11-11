package com.mycompany.rpg.java.itens;

public class Pd extends Item {
    //Variáveis
    private int dano; // dano causado ao inimigo

    public Pd() {

        super("Poção de Dano", 70);
        this.dano = 100;

    }

    public int getDano() {

        return dano;

    }

}//Fim da Classe