package com.mycompany.rpg.java.itens;

public class Item {
    //Variáveis
    private String nome;
    private int preco; // preço em rosários

    public Item(String nome, int preco) {

        this.nome = nome;
        this.preco = preco;

    }

    public String getNome() {

        return nome;

    }

    public int getPreco() {

        return preco;

    }

}//Fim da Classe