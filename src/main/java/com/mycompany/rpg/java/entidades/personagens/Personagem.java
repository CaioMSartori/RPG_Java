
package com.mycompany.rpg.java.entidades.personagens;

import com.mycompany.rpg.java.entidades.inimigos.Inimigo;
import com.mycompany.rpg.java.itens.Arma;
import com.mycompany.rpg.java.itens.Armadura;
import com.mycompany.rpg.java.itens.Inventario;
import com.mycompany.rpg.java.itens.Item;
import com.mycompany.rpg.java.itens.Pc;
import com.mycompany.rpg.java.itens.Pd;

public class Personagem {

    // Atributos do personagem
    private String nome;
    private int vida;
    private int vidaMaxima;
    private int dano;
    private int moedas;

    // Inventário e equipamentos
    private Inventario inventario;
    private Arma arma;
    private Armadura armadura;

    // Construtor
    public Personagem(String nome, int vida, int dano) {

        this.nome = nome;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.dano = dano;
        this.inventario = new Inventario(10);
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getDano() {
        return dano;
    }

    public int getMoedas() {
        return moedas;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public Arma getArma() {
        return arma;
    }

    public Armadura getArmadura() {
        return armadura;
    }

    // Adiciona moedas ao personagem
    public void adicionarMoedas(int quantidade) {

        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "Quantidade negativa.");
        }

        moedas = Math.addExact(moedas, quantidade);
    }

    // Retira moedas caso o personagem tenha saldo suficiente
    public boolean gastarMoedas(int quantidade) {

        if (quantidade < 0 || quantidade > moedas) {
            return false;
        }

        moedas -= quantidade;

        return true;
    }

    // Dano base + dano da arma equipada
    public int getDanoTotal() {

        if (arma != null) {
            return dano + arma.getDano();
        }

        return dano;
    }

    // Defesa da armadura equipada
    public int getDefesa() {

        if (armadura != null) {
            return armadura.getDefesa();
        }

        return 0;
    }

    // Ataca um inimigo
    public void Bater(Inimigo alvo) {

        if (vida > 0 && alvo.getVida() > 0) {

            System.out.println(
                    nome + " atacou " + alvo.getNome() + "!");

            alvo.TomarPorrada(getDanoTotal());
        }
    }

    // Recebe dano
    public void TomarPorrada(int dano) {

        // A armadura reduz o dano,
        // mas todo golpe causa pelo menos 1
        int danoReal = Math.max(1, dano - getDefesa());

        vida = vida - danoReal;

        if (vida <= 0) {

            vida = 0;

            System.out.println(nome + " morreu!");

        } else {

            System.out.println(
                    nome + " possui " + vida + " de vida.");
        }
    }

    // Recupera vida
    public void curar(int quantidade) {

        if (vida <= 0) {

            System.out.println(
                    nome + " está morto e não pode ser curado.");

            return;
        }

        vida = Math.min(vidaMaxima, vida + quantidade);

        System.out.println(
                nome + " recuperou vida e agora tem "
                + vida + "/" + vidaMaxima + ".");
    }

    // Usa um item que não precisa de alvo
    public boolean usar(Item item) {

        if (!inventario.contem(item)) {

            System.out.println(
                    nome + " não tem "
                    + item.getNome() + " no inventário.");

            return false;
        }

        // Poção de cura
        if (item instanceof Pc) {

            curar(((Pc) item).getCura());

            inventario.remover(item);

            return true;
        }

        // Equipar arma
        if (item instanceof Arma) {

            inventario.remover(item);

            if (arma != null) {

                // A arma antiga retorna ao inventário
                inventario.adicionar(arma);
            }

            arma = (Arma) item;

            System.out.println(
                    nome + " equipou " + arma.getNome() + ".");

            return true;
        }

        // Equipar armadura
        if (item instanceof Armadura) {

            inventario.remover(item);

            if (armadura != null) {

                // A armadura antiga retorna ao inventário
                inventario.adicionar(armadura);
            }

            armadura = (Armadura) item;

            System.out.println(
                    nome + " vestiu " + armadura.getNome() + ".");

            return true;
        }

        System.out.println(
                item.getNome() + " ainda não pode ser usado.");

        return false;
    }

    // Usa um item que precisa de um alvo
    public boolean usar(Item item, Inimigo alvo) {

        // Poção de dano
        if (item instanceof Pd) {

            if (!inventario.contem(item)) {

                System.out.println(
                        nome + " não tem "
                        + item.getNome() + " no inventário.");

                return false;
            }

            if (vida <= 0 || alvo.getVida() <= 0) {
                return false;
            }

            System.out.println(
                    nome + " jogou " + item.getNome()
                    + " em " + alvo.getNome() + "!");

            alvo.TomarPorrada(((Pd) item).getDano());

            inventario.remover(item);

            return true;
        }

        return usar(item);
    }
}
