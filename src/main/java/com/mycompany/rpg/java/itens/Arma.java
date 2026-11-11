package com.mycompany.rpg.java.itens;

public class Arma extends Item {
    //Variáveis
    private int dano;

    public Arma(String nome, int preco, int dano) {

        super(nome, preco);
        this.dano = dano;

    }

    public int getDano() {

        return dano;

    }

}//Fim da Classe