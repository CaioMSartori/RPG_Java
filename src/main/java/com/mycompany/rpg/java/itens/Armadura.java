package com.mycompany.rpg.java.itens;

public class Armadura extends Item {
    //Variáveis
    private int defesa; // quanto de dano a armadura absorve por golpe

    public Armadura(String nome, int preco, int defesa) {

        super(nome, preco);
        this.defesa = defesa;

    }

    public int getDefesa() {

        return defesa;

    }

}//Fim da Classe