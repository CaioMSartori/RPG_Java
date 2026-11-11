package com.mycompany.rpg.java.entidades.inimigos;
import java.util.Random;
import java.util.Scanner;

public class Boss {

    // --- Atributos do Boss ---
    String nomeBoss;
    int vidaMaxBoss, vidaAtualBoss, ataqueBoss, defesaBoss;
    String tipoBoss;
    String descricaoEspecial;

    // --- Atributos do Jogador ---
    String nomeJogador;
    int vidaMaxJogador, vidaAtualJogador, ataqueJogador;

    Random random = new Random();
    Scanner scanner = new Scanner(System.in);

    // Define o boss pelo índice (0 a 6)
    void definirBoss(int indice) {
        switch (indice) {
            case 0 -> {
                nomeBoss = "Dragão de Fogo";
                vidaMaxBoss = 150; ataqueBoss = 25; defesaBoss = 5;
                tipoBoss = "Fogo";
                descricaoEspecial = "lança uma explosão de chamas!";
            }
            case 1 -> {
                nomeBoss = "Golem de Gelo";
                vidaMaxBoss = 200; ataqueBoss = 15; defesaBoss = 15;
                tipoBoss = "Gelo";
                descricaoEspecial = "congela o terreno!";
            }
            case 2 -> {
                nomeBoss = "Titã Elétrico";
                vidaMaxBoss = 120; ataqueBoss = 30; defesaBoss = 2;
                tipoBoss = "Elétrico";
                descricaoEspecial = "descarrega um raio poderoso!";
            }
            case 3 -> {
                nomeBoss = "Cobra Venenosa";
                vidaMaxBoss = 100; ataqueBoss = 20; defesaBoss = 8;
                tipoBoss = "Veneno";
                descricaoEspecial = "libera uma nuvem de gás tóxico!";
            }
            case 4 -> {
                nomeBoss = "Golem de Terra";
                vidaMaxBoss = 250; ataqueBoss = 10; defesaBoss = 20;
                tipoBoss = "Terra";
                descricaoEspecial = "causa um terremoto!";
            }
            case 5 -> {
                nomeBoss = "Fênix do Vento";
                vidaMaxBoss = 110; ataqueBoss = 28; defesaBoss = 3;
                tipoBoss = "Ar";
                descricaoEspecial = "cria uma tempestade de lâminas!";
            }
            case 6 -> {
                nomeBoss = "Sombra Eterna";
                vidaMaxBoss = 180; ataqueBoss = 22; defesaBoss = 10;
                tipoBoss = "Escuro";
                descricaoEspecial = "drena a alma do jogador!";
            }
        }
        vidaAtualBoss = vidaMaxBoss;
    }

    boolean bossVivo() { return vidaAtualBoss > 0; }
    boolean jogadorVivo() { return vidaAtualJogador > 0; }

    void jogadorAtaca() {
        int dano = Math.max(0, ataqueJogador - defesaBoss);
        vidaAtualBoss -= dano;
        if (vidaAtualBoss < 0) vidaAtualBoss = 0;
        System.out.println(nomeJogador + " ataca " + nomeBoss + " causando " + dano + " de dano.");
    }

    void jogadorCura() {
        int cura = random.nextInt(10) + 10;
        vidaAtualJogador = Math.min(vidaMaxJogador, vidaAtualJogador + cura);
        System.out.println(nomeJogador + " se curou em " + cura + " HP.");
    }

    void bossAtaca() {
        System.out.println(nomeBoss + " " + descricaoEspecial);
        int dano = ataqueBoss;
        vidaAtualJogador -= dano;
        if (vidaAtualJogador < 0) vidaAtualJogador = 0;
        System.out.println("Você recebeu " + dano + " de dano. HP restante: " + vidaAtualJogador);
    }

    void exibirStatus() {
        System.out.println("\n--- " + nomeBoss + " [" + tipoBoss + "] HP: " + vidaAtualBoss + "/" + vidaMaxBoss + " ---");
        System.out.println(nomeJogador + " HP: " + vidaAtualJogador + "/" + vidaMaxJogador);
    }

    void batalha() {
        int turno = 1;
        while (jogadorVivo() && bossVivo()) {
            System.out.println("\n=== Turno " + turno + " ===");
            exibirStatus();

            System.out.println("\n1. Atacar  |  2. Curar");
            System.out.print("Escolha: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> jogadorAtaca();
                case 2 -> jogadorCura();
                default -> System.out.println("Opção inválida, turno perdido.");
            }

            if (!bossVivo()) {
                System.out.println("\n" + nomeBoss + " foi derrotado! Vitória!");
                return;
            }

            bossAtaca();
            turno++;
        }
        if (!jogadorVivo())
            System.out.println("\n" + nomeJogador + " foi derrotado... Game Over.");
    }

    public static void main(String[] args) {
        // CORRIGIDO: Agora instancia a classe "Bosses" corretamente
        Boss jogo = new Boss();

        System.out.print("Digite seu nome: ");
        jogo.nomeJogador = jogo.scanner.nextLine();
        jogo.vidaMaxJogador = 100;
        jogo.vidaAtualJogador = 100;
        jogo.ataqueJogador = 15;

        int indice = jogo.random.nextInt(7);
        jogo.definirBoss(indice);

        System.out.println("\nUm boss de " + jogo.tipoBoss + " apareceu: " + jogo.nomeBoss + "!");
        jogo.batalha();

        jogo.scanner.close();
    }
}