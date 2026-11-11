package com.mycompany.rpg.java.itens;

public class Pm extends Item {
    //Variáveis
    private int mana; // quantidade de mana recuperada

    public Pm() {

        super("Poção de Recuperação de Mana", 50);
        this.mana = 80;

    }

    public int getMana() {

        return mana;

    }

}//Fim da Classe