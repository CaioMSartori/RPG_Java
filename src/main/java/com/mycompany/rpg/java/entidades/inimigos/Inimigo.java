package com.mycompany.rpg.java.entidades.inimigos;

public class Inimigo {

    private String nome;
    private int vida;
    private int dano;

    public Inimigo(String nome, int vida, int dano) {
        this.nome = nome;
        this.vida = vida;
        this.dano = dano;
    }

    public String getNome() { return nome; }
    public int getVida() { return vida; }
    public int getDano() { return dano; }

    public void TomarPorrada(int dano) {
        vida = vida - dano;

        if (vida <= 0) {
            vida = 0;
            System.out.println(nome + " morreu!");
        } else {
            System.out.println(nome + " possui " + vida + " de vida.");
        }
    }
}