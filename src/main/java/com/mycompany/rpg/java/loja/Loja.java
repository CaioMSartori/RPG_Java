
package com.mycompany.rpg.java.loja;

import com.mycompany.rpg.java.entidades.personagens.Personagem;
import com.mycompany.rpg.java.itens.*;

import javax.swing.*;
import java.awt.*;

public class Loja extends JFrame {

    private Personagem jogador =
            new Personagem("Aventureiro", 250, 30);

    private int moedas = 500;

    private DefaultListModel<Item> modeloLoja =
            new DefaultListModel<>();

    private DefaultListModel<String> modeloInventario =
            new DefaultListModel<>();

    private JList<Item> listaLoja;
    private JList<String> listaInventario;

    private JLabel moedasLabel;
    private JLabel detalhesLabel;
    private JLabel mensagemLabel;

    public Loja() {

        setTitle("Loja RPG");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ITENS DISPONÍVEIS
        modeloLoja.addElement(new Pc());
        modeloLoja.addElement(new Pm());
        modeloLoja.addElement(new Pd());

        // TÍTULO
        JLabel titulo = new JLabel(
                "LOJA DO AVENTUREIRO",
                JLabel.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        // MOEDAS
        moedasLabel = new JLabel(
                "Moedas: " + moedas
        );

        moedasLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        // LISTA DA LOJA
        listaLoja = new JList<>(modeloLoja);

        listaLoja.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        listaLoja.setFont(new Font("Arial", Font.PLAIN, 16));

        listaLoja.setCellRenderer((lista, item, indice, selecionado, foco) -> {

            JLabel label = new JLabel(
                    item.getNome() + " - " +
                    item.getPreco() + " moedas"
            );

            label.setOpaque(true);
            label.setBorder(
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            );

            if (selecionado) {
                label.setBackground(Color.LIGHT_GRAY);
            } else {
                label.setBackground(Color.WHITE);
            }

            return label;
        });

        JScrollPane scrollLoja = new JScrollPane(listaLoja);

        // DETALHES DO ITEM
        detalhesLabel = new JLabel(
                "Escolha um item."
        );

        // BOTÃO COMPRAR
        JButton comprar = new JButton("COMPRAR ITEM");

        comprar.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        comprar.addActionListener(e -> comprarItem());

        // INVENTÁRIO
        listaInventario = new JList<>(modeloInventario);

        JScrollPane scrollInventario =
                new JScrollPane(listaInventario);

        // MENSAGEM
        mensagemLabel = new JLabel(
                "Bem-vindo à loja!"
        );

        // PAINEL DA LOJA
        JPanel painelLoja = new JPanel(new BorderLayout(10, 10));

        painelLoja.setBorder(
                BorderFactory.createTitledBorder("ITENS À VENDA")
        );

        painelLoja.add(scrollLoja, BorderLayout.CENTER);

        JPanel parteInferior = new JPanel(new GridLayout(2, 1, 5, 5));

        parteInferior.add(detalhesLabel);
        parteInferior.add(comprar);

        painelLoja.add(parteInferior, BorderLayout.SOUTH);

        // PAINEL DO INVENTÁRIO
        JPanel painelInventario = new JPanel(new BorderLayout());

        painelInventario.setBorder(
                BorderFactory.createTitledBorder("ITEM COMPRADO / INVENTÁRIO")
        );

        painelInventario.add(
                scrollInventario,
                BorderLayout.CENTER
        );

        // SELEÇÃO DO ITEM
        listaLoja.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                Item item = listaLoja.getSelectedValue();

                mostrarDetalhes(item);
            }
        });

        // PAINEL SUPERIOR
        JPanel superior = new JPanel(new GridLayout(2, 1));

        superior.add(titulo);
        superior.add(moedasLabel);

        // PAINEL CENTRAL
        JPanel centro = new JPanel(new GridLayout(1, 2, 15, 15));

        centro.add(painelLoja);
        centro.add(painelInventario);

        // TELA PRINCIPAL
        setLayout(new BorderLayout(10, 10));

        add(superior, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(mensagemLabel, BorderLayout.SOUTH);

        atualizarInventario();
    }

    // MOSTRAR DETALHES
    private void mostrarDetalhes(Item item) {

        if (item == null) {
            detalhesLabel.setText("Escolha um item.");
            return;
        }

        String detalhes = item.getNome();

        detalhes += " | Preço: " + item.getPreco();

        if (item instanceof Pc) {

            detalhes += " | Cura: " +
                    ((Pc) item).getCura();

        } else if (item instanceof Pm) {

            detalhes += " | Mana: " +
                    ((Pm) item).getMana();

        } else if (item instanceof Pd) {

            detalhes += " | Dano: " +
                    ((Pd) item).getDano();
        }

        detalhesLabel.setText(detalhes);
    }

    // COMPRAR ITEM
    private void comprarItem() {

        Item item = listaLoja.getSelectedValue();

        if (item == null) {

            mensagemLabel.setText(
                    "Selecione um item primeiro!"
            );

            return;
        }

        if (moedas < item.getPreco()) {

            mensagemLabel.setText(
                    "Você não possui moedas suficientes!"
            );

            return;
        }

        if (jogador.getInventario().estaCheio()) {

            mensagemLabel.setText(
                    "Seu inventário está cheio!"
            );

            return;
        }

        boolean comprado =
                jogador.getInventario().adicionar(item);

        if (comprado) {

            moedas -= item.getPreco();

            modeloLoja.removeElement(item);

            moedasLabel.setText(
                    "Moedas: " + moedas
            );

            mensagemLabel.setText(
                    "ITEM COMPRADO: " + item.getNome()
            );

            atualizarInventario();

            detalhesLabel.setText(
                    "Compra realizada com sucesso!"
            );
        }
    }

    // ATUALIZAR INVENTÁRIO
    private void atualizarInventario() {

        modeloInventario.clear();

        for (Item item :
                jogador.getInventario().getItens()) {

            modeloInventario.addElement(
                    item.getNome()
            );
        }

        if (modeloInventario.isEmpty()) {

            modeloInventario.addElement(
                    "Inventário vazio"
            );
        }
    }
}