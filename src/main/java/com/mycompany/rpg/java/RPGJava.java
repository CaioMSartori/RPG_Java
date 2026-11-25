
package com.mycompany.rpg.java;

import com.mycompany.rpg.java.combate.Batalha;

import com.mycompany.rpg.java.entidades.personagens.Personagem;
import com.mycompany.rpg.java.entidades.personagens.Guerreiro;
import com.mycompany.rpg.java.entidades.personagens.Barbaro;
import com.mycompany.rpg.java.entidades.personagens.Mago;
import com.mycompany.rpg.java.entidades.personagens.Arqueiro;

import com.mycompany.rpg.java.entidades.inimigos.Inimigo;
import com.mycompany.rpg.java.entidades.inimigos.Zumbi;
import com.mycompany.rpg.java.entidades.inimigos.Esqueleto;
import com.mycompany.rpg.java.entidades.inimigos.Goblin;
import com.mycompany.rpg.java.entidades.inimigos.Boss;

import com.mycompany.rpg.java.itens.Item;
import com.mycompany.rpg.java.itens.Arco;
import com.mycompany.rpg.java.itens.Clava;
import com.mycompany.rpg.java.itens.Lança;
import com.mycompany.rpg.java.itens.Vara;
import com.mycompany.rpg.java.itens.Ac;
import com.mycompany.rpg.java.itens.Pc;
import com.mycompany.rpg.java.itens.Pd;

import java.util.List;
import java.util.Scanner;

public class RPGJava {

    private final Scanner scanner;
    private final Batalha batalha;

    private Personagem jogador;

    // Construtor
    public RPGJava(Scanner scanner) {

        this.scanner = scanner;
        this.batalha = new Batalha(scanner);
    }

    // Ponto de entrada do programa
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            RPGJava jogo = new RPGJava(scanner);

            jogo.iniciar();
        }
    }

    // ==================================================
    // INICIALIZAÇÃO DO JOGO
    // ==================================================

    public void iniciar() {

        boolean executando = true;

        while (executando) {

            exibirMenuInicial();

            int opcao = lerOpcao(1, 2);

            switch (opcao) {

                case 1 -> novoJogo();

                case 2 -> executando = false;
            }
        }

        System.out.println("\nObrigado por jogar!");
    }

    private void exibirMenuInicial() {

        System.out.println("\n==================================");
        System.out.println("              JAVA RPG");
        System.out.println("==================================");

        System.out.println("1. Novo jogo");
        System.out.println("2. Sair");
    }

    // ==================================================
    // CRIAÇÃO DE UM NOVO JOGO
    // ==================================================

    private void novoJogo() {

        jogador = criarPersonagem();

        fornecerItensIniciais();

        System.out.println("\nSua aventura começou!");

        System.out.println(
                "Bem-vindo, " + jogador.getNome() + "!");

        menuAventura();
    }

    // ==================================================
    // CRIAÇÃO DO PERSONAGEM
    // ==================================================

    private Personagem criarPersonagem() {

        String nome;

        do {

            System.out.print("\nDigite o nome do personagem: ");

            nome = scanner.nextLine().trim();

            if (nome.isEmpty()) {

                System.out.println(
                        "O nome não pode estar vazio.");
            }

        } while (nome.isEmpty());

        System.out.println("\n=== ESCOLHA SUA CLASSE ===");

        System.out.println("1. Guerreiro");
        System.out.println("2. Bárbaro");
        System.out.println("3. Mago");
        System.out.println("4. Arqueiro");

        int opcao = lerOpcao(1, 4);

        return switch (opcao) {

            case 1 -> new Guerreiro(nome);

            case 2 -> new Barbaro(nome);

            case 3 -> new Mago(nome);

            case 4 -> new Arqueiro(nome);

            default -> throw new IllegalStateException(
                    "Classe inválida.");
        };
    }

    // ==================================================
    // EQUIPAMENTOS E ITENS INICIAIS
    // ==================================================

    private void fornecerItensIniciais() {

        Item armaInicial;

        // Cada personagem recebe uma arma correspondente
        if (jogador instanceof Guerreiro) {

            armaInicial = new Lança();

        } else if (jogador instanceof Barbaro) {

            armaInicial = new Clava();

        } else if (jogador instanceof Mago) {

            armaInicial = new Vara();

        } else {

            armaInicial = new Arco();
        }

        Item armaduraInicial = new Ac();

        // Adiciona os equipamentos ao inventário
        jogador.getInventario().adicionar(armaInicial);
        jogador.getInventario().adicionar(armaduraInicial);

        // Equipa os itens
        jogador.usar(armaInicial);
        jogador.usar(armaduraInicial);

        // Adiciona poções iniciais
        jogador.getInventario().adicionar(new Pc());
        jogador.getInventario().adicionar(new Pc());
        jogador.getInventario().adicionar(new Pd());

        // Fornece moedas iniciais
        jogador.adicionarMoedas(100);

        System.out.println("\n=== ITENS INICIAIS ===");

        System.out.println("Arma: " + armaInicial.getNome());
        System.out.println("Armadura: " + armaduraInicial.getNome());
        System.out.println("2 poções de cura");
        System.out.println("1 poção de dano");
        System.out.println("100 moedas");
    }

    // ==================================================
    // MENU PRINCIPAL DA AVENTURA
    // ==================================================

    private void menuAventura() {

        boolean aventurando = true;

        while (aventurando && jogador.getVida() > 0) {

            System.out.println("\n==================================");
            System.out.println("         MENU DA AVENTURA");
            System.out.println("==================================");

            System.out.println("Jogador: " + jogador.getNome());

            System.out.println(
                    "Vida: " + jogador.getVida()
                    + "/" + jogador.getVidaMaxima());

            System.out.println(
                    "Moedas: " + jogador.getMoedas());

            System.out.println("\n1. Iniciar batalha");
            System.out.println("2. Consultar personagem");
            System.out.println("3. Consultar inventário");
            System.out.println("4. Usar ou equipar item");
            System.out.println("5. Visitar loja");
            System.out.println("6. Consultar missões");
            System.out.println("7. Voltar ao menu inicial");

            int opcao = lerOpcao(1, 7);

            switch (opcao) {

                case 1 -> menuBatalha();

                case 2 -> exibirPersonagem();

                case 3 -> exibirInventario();

                case 4 -> menuInventario();

                case 5 -> visitarLoja();

                case 6 -> consultarMissoes();

                case 7 -> {

                    System.out.println(
                            "O progresso atual será perdido.");

                    System.out.println("1. Confirmar");
                    System.out.println("2. Cancelar");

                    if (lerOpcao(1, 2) == 1) {

                        aventurando = false;
                    }
                }
            }
        }

        if (jogador.getVida() <= 0) {

            exibirGameOver();
        }
    }

    // ==================================================
    // MENU DE BATALHAS
    // ==================================================

    private void menuBatalha() {

        System.out.println("\n=== ESCOLHA SUA BATALHA ===");

        System.out.println("1. Enfrentar um zumbi");
        System.out.println("2. Enfrentar dois zumbis");
        System.out.println("3. Enfrentar um esqueleto");
        System.out.println("4. Enfrentar um goblin");
        System.out.println("5. Enfrentar um grupo de inimigos");
        System.out.println("6. Enfrentar um chefe elemental");
        System.out.println("7. Voltar");

        int opcao = lerOpcao(1, 7);

        List<Inimigo> inimigos;

        switch (opcao) {

            case 1 -> inimigos = List.of(
                    new Zumbi("Zumbi")
            );

            case 2 -> inimigos = List.of(
                    new Zumbi("Zumbi 1"),
                    new Zumbi("Zumbi 2")
            );

            case 3 -> inimigos = List.of(
                    new Esqueleto("Esqueleto")
            );

            case 4 -> inimigos = List.of(
                    new Goblin("Goblin")
            );

            case 5 -> inimigos = List.of(
                    new Zumbi("Zumbi"),
                    new Esqueleto("Esqueleto"),
                    new Goblin("Goblin")
            );

            case 6 -> inimigos = List.of(
                    Boss.sortearBoss()
            );

            default -> {
                return;
            }
        }

        System.out.println("\n=== INICIANDO BATALHA ===");

        boolean venceu = batalha.iniciarBatalha(
                jogador, inimigos);

        if (venceu) {

            System.out.println(
                    "\nVocê sobreviveu à batalha!");

            System.out.println(
                    "Vida restante: " + jogador.getVida());

            System.out.println(
                    "Moedas acumuladas: " + jogador.getMoedas());
        }
    }

    // ==================================================
    // INFORMAÇÕES DO PERSONAGEM
    // ==================================================

    private void exibirPersonagem() {

        System.out.println("\n=== FICHA DO PERSONAGEM ===");

        System.out.println(
                "Nome: " + jogador.getNome());

        System.out.println(
                "Classe: " + jogador.getClass().getSimpleName());

        System.out.println(
                "Vida: " + jogador.getVida()
                + "/" + jogador.getVidaMaxima());

        System.out.println(
                "Dano base: " + jogador.getDano());

        System.out.println(
                "Dano total: " + jogador.getDanoTotal());

        System.out.println(
                "Defesa: " + jogador.getDefesa());

        System.out.println(
                "Moedas: " + jogador.getMoedas());

        System.out.println("\n=== EQUIPAMENTOS ===");

        if (jogador.getArma() != null) {

            System.out.println(
                    "Arma: " + jogador.getArma().getNome());

        } else {

            System.out.println("Arma: Nenhuma");
        }

        if (jogador.getArmadura() != null) {

            System.out.println(
                    "Armadura: "
                    + jogador.getArmadura().getNome());

        } else {

            System.out.println("Armadura: Nenhuma");
        }
    }

    // ==================================================
    // INVENTÁRIO
    // ==================================================

    private void exibirInventario() {

        System.out.println("\n=== INVENTÁRIO ===");

        jogador.getInventario().listar();
    }

    private void menuInventario() {

        List<Item> itens =
                jogador.getInventario().getItens();

        if (itens.isEmpty()) {

            System.out.println("\nSeu inventário está vazio.");

            return;
        }

        System.out.println("\n=== USAR OU EQUIPAR ITEM ===");

        for (int i = 0; i < itens.size(); i++) {

            System.out.println(
                    (i + 1) + ". " + itens.get(i).getNome());
        }

        System.out.println("0. Voltar");

        int opcao = lerOpcao(0, itens.size());

        if (opcao == 0) {

            return;
        }

        Item itemSelecionado = itens.get(opcao - 1);

        // Poções de dano precisam de um inimigo como alvo
        if (itemSelecionado instanceof Pd) {

            System.out.println(
                    "Este item só pode ser usado em batalha.");

            return;
        }

        // Evita desperdício de poções de cura
        if (itemSelecionado instanceof Pc
                && jogador.getVida() == jogador.getVidaMaxima()) {

            System.out.println("Sua vida já está cheia.");

            return;
        }

        jogador.usar(itemSelecionado);
    }

    // ==================================================
    // LOJA
    // ==================================================

    private void visitarLoja() {

        // TODO: Integrar o sistema de loja
        // desenvolvido pelo responsável por Loja.java.
        //
        // A loja deverá receber o objeto jogador
        // já existente, sem criar outro personagem.

        System.out.println("\n=== LOJA ===");

        System.out.println(
                "A loja ainda está em desenvolvimento.");

        System.out.println(
                "Saldo disponível: " + jogador.getMoedas());
    }

    // ==================================================
    // MISSÕES
    // ==================================================

    private void consultarMissoes() {

        // TODO: Integrar o sistema de missões
        // desenvolvido pelo responsável por Missao.java.
        //
        // As missões deverão utilizar o jogador
        // e o controlador de batalha já existentes.

        System.out.println("\n=== MISSÕES ===");

        System.out.println(
                "O sistema de missões está em desenvolvimento.");
    }

    // ==================================================
    // ENCERRAMENTO DA AVENTURA
    // ==================================================

    private void exibirGameOver() {

        System.out.println("\n==================================");
        System.out.println("             GAME OVER");
        System.out.println("==================================");

        System.out.println(
                jogador.getNome() + " foi derrotado.");

        System.out.println(
                "Moedas acumuladas: " + jogador.getMoedas());

        System.out.println(
                "Você retornará ao menu inicial.");
    }

    // ==================================================
    // VALIDAÇÃO DE ENTRADA
    // ==================================================

    private int lerOpcao(int minimo, int maximo) {

        while (true) {

            System.out.print("\nEscolha uma opção: ");

            String entrada = scanner.nextLine().trim();

            try {

                int opcao = Integer.parseInt(entrada);

                if (opcao >= minimo && opcao <= maximo) {

                    return opcao;
                }

            } catch (NumberFormatException e) {

                // Entrada não numérica.
            }

            System.out.println(
                    "Opção inválida. Tente novamente.");
        }
    }
}
