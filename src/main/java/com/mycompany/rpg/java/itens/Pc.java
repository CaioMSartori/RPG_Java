package com.mycompany.rpg.java.itens;

public class Pc extends Item {
    //Variáveis
    private int cura; // quantidade de vida recuperada

    public Pc() {

        super("Poção de Cura", 60);
        this.cura = 100;

    }

    public int getCura() {

        return cura;

    }

}//Fim da Classe