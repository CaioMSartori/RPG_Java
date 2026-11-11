package com.mycompany.rpg.java.entidades.personagens;

import com.mycompany.rpg.java.entidades.inimigos.Inimigo;
import com.mycompany.rpg.java.itens.Arma;
import com.mycompany.rpg.java.itens.Armadura;
import com.mycompany.rpg.java.itens.Inventario;
import com.mycompany.rpg.java.itens.Item;
import com.mycompany.rpg.java.itens.Pc;
import com.mycompany.rpg.java.itens.Pd;

public class Personagem {

    // Variáveis e Nomes
    private String nome;
    private int vida;
    private int vidaMaxima;
    private int dano;

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

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    // Dano base do personagem (sem a arma)
    public int getDano() {
        return dano;
    }

    // Dano base + dano da arma equipada
    public int getDanoTotal() {
        if (arma != null) {
            return dano + arma.getDano();
        }
        return dano;
    }

    // Defesa da armadura equipada (0 se não tiver)
    public int getDefesa() {
        if (armadura != null) {
            return armadura.getDefesa();
        }
        return 0;
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

    public void Bater(Inimigo alvo) {
        if (vida > 0 && alvo.getVida() > 0) {
            System.out.println(nome + " atacou " + alvo.getNome() + "!");
            alvo.TomarPorrada(getDanoTotal());
        }
    }

    public void TomarPorrada(int dano) {
        // A armadura reduz o dano, mas todo golpe causa pelo menos 1
        int danoReal = Math.max(1, dano - getDefesa());
        vida = vida - danoReal;

        if (vida <= 0) {
            vida = 0;
            System.out.println(nome + " morreu!");
        } else {
            System.out.println(nome + " possui " + vida + " de vida.");
        }
    }

    public void curar(int quantidade) {
        if (vida <= 0) {
            System.out.println(nome + " está morto e não pode ser curado.");
            return;
        }

        vida = Math.min(vidaMaxima, vida + quantidade);
        System.out.println(nome + " recuperou vida e agora tem " + vida + "/" + vidaMaxima + ".");
    }

    // Usa um item que está no inventário (poção de cura, arma ou armadura)
    public boolean usar(Item item) {
        if (!inventario.contem(item)) {
            System.out.println(nome + " não tem " + item.getNome() + " no inventário.");
            return false;
        }

        if (item instanceof Pc) {
            curar(((Pc) item).getCura());
            inventario.remover(item);
            return true;
        }

        if (item instanceof Arma) {
            inventario.remover(item);
            if (arma != null) {
                inventario.adicionar(arma); // a arma antiga volta para o inventário
            }
            arma = (Arma) item;
            System.out.println(nome + " equipou " + arma.getNome() + ".");
            return true;
        }

        if (item instanceof Armadura) {
            inventario.remover(item);
            if (armadura != null) {
                inventario.adicionar(armadura); // a armadura antiga volta para o inventário
            }
            armadura = (Armadura) item;
            System.out.println(nome + " vestiu " + armadura.getNome() + ".");
            return true;
        }

        System.out.println(item.getNome() + " ainda não pode ser usado.");
        return false;
    }

    // Usa um item que precisa de alvo (poção de dano)
    public boolean usar(Item item, Inimigo alvo) {
        if (item instanceof Pd) {
            if (!inventario.contem(item)) {
                System.out.println(nome + " não tem " + item.getNome() + " no inventário.");
                return false;
            }

            if (vida <= 0 || alvo.getVida() <= 0) {
                return false;
            }

            System.out.println(nome + " jogou " + item.getNome() + " em " + alvo.getNome() + "!");
            alvo.TomarPorrada(((Pd) item).getDano());
            inventario.remover(item);
            return true;
        }

        return usar(item);
    }
}//Fim da Classe