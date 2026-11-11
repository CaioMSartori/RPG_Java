package com.mycompany.rpg.java.itens;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    //Variáveis
    private List<Item> itens;
    private int capacidade;

    public Inventario(int capacidade) {

        this.itens = new ArrayList<>();
        this.capacidade = capacidade;

    }

    public boolean adicionar(Item item) {

        if (estaCheio()) {
            System.out.println("Inventário cheio! Não foi possível guardar " + item.getNome() + ".");
            return false;
        }

        itens.add(item);
        return true;

    }

    public boolean remover(Item item) {

        return itens.remove(item);

    }

    public boolean contem(Item item) {

        return itens.contains(item);

    }

    public boolean estaCheio() {

        return itens.size() >= capacidade;

    }

    public int getQuantidade() {

        return itens.size();

    }

    public int getCapacidade() {

        return capacidade;

    }

    public List<Item> getItens() {

        return new ArrayList<>(itens); // cópia, para ninguém mexer na lista por fora

    }

    public void listar() {

        System.out.println("Inventário (" + itens.size() + "/" + capacidade + "):");

        if (itens.isEmpty()) {
            System.out.println("  (vazio)");
            return;
        }

        for (int i = 0; i < itens.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + itens.get(i).getNome());
        }

    }

}//Fim da Classe