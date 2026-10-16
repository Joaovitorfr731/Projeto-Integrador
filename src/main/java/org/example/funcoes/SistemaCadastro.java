package org.example.funcoes;

import java.util.Scanner;

/*
 * DESAFIO: Sistema de Cadastro com Menu e Funções
 * Os usuários ficam guardados em uma matriz (cada linha = um usuário,
 * cada coluna = um dado). Cada opção do menu é uma função separada.
 */
public class SistemaCadastro {

    static final int MAX_USUARIOS = 10;
    static final String[] CABECALHO = {"ID", "NOME", "TELEFONE", "EMAIL"};

    // Matriz: [linha = usuário][coluna = nome, telefone, email]
    static String[][] usuarios = new String[MAX_USUARIOS][CABECALHO.length - 1];
    static int totalUsuarios = 0;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            opcao = exibirMenu();
            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> consultarUsuarios();
                case 3 -> atualizarUsuario();
                case 4 -> deletarUsuario();
                case 0 -> System.out.println("Saindo do sistema... Até logo!");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
        sc.close();
    }

    // ===================== MENU =====================
    public static int exibirMenu() {
        System.out.println();
        System.out.println("========= SISTEMA DE CADASTRO =========");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Consultar usuários");
        System.out.println("3 - Atualizar usuário");
        System.out.println("4 - Deletar usuário");
        System.out.println("0 - Sair");
        System.out.println("=======================================");
        return lerInteiro("Escolha uma opção: ");
    }

    // ===================== OPÇÕES =====================
    public static void cadastrarUsuario() {
        if (totalUsuarios == MAX_USUARIOS) {
            System.out.println("Limite de " + MAX_USUARIOS + " usuários atingido!");
            return;
        }
        System.out.println("\n--- NOVO USUÁRIO ---");
        preencherDados(totalUsuarios);   // reaproveitado no atualizar
        totalUsuarios++;
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public static void consultarUsuarios() {
        if (!existeUsuario()) {
            return;
        }
        System.out.println();
        imprimirLinha(CABECALHO);
        System.out.println("-".repeat(5 + 20 + 15 + 30));
        for (int i = 0; i < totalUsuarios; i++) {
            String[] linha = {String.valueOf(i + 1), usuarios[i][0], usuarios[i][1], usuarios[i][2]};
            imprimirLinha(linha);
        }
        System.out.println("Total: " + totalUsuarios + " usuário(s).");
    }

    public static void atualizarUsuario() {
        if (!existeUsuario()) {
            return;
        }
        consultarUsuarios();
        int indice = escolherUsuario("Digite o ID do usuário que deseja atualizar: ");
        if (indice == -1) {
            return;
        }
        System.out.println("\n--- ATUALIZANDO USUÁRIO " + (indice + 1) + " ---");
        preencherDados(indice);
        System.out.println("Usuário atualizado com sucesso!");
    }

    public static void deletarUsuario() {
        if (!existeUsuario()) {
            return;
        }
        consultarUsuarios();
        int indice = escolherUsuario("Digite o ID do usuário que deseja deletar: ");
        if (indice == -1) {
            return;
        }
        String nome = usuarios[indice][0];

        // "Puxa" todos os usuários de baixo uma linha para cima
        for (int i = indice; i < totalUsuarios - 1; i++) {
            usuarios[i] = usuarios[i + 1];
        }
        totalUsuarios--;
        usuarios[totalUsuarios] = new String[CABECALHO.length - 1]; // limpa a última linha

        System.out.println("Usuário \"" + nome + "\" removido com sucesso!");
    }

    // ===================== FUNÇÕES AUXILIARES =====================

    // Lê nome, telefone e email e grava na linha indicada da matriz
    public static void preencherDados(int linha) {
        usuarios[linha][0] = lerTexto("Nome: ");
        usuarios[linha][1] = lerTexto("Telefone: ");
        usuarios[linha][2] = lerTexto("Email: ");
    }

    // Pede um ID e devolve o índice na matriz, ou -1 se for inválido
    public static int escolherUsuario(String mensagem) {
        int id = lerInteiro(mensagem);
        if (id < 1 || id > totalUsuarios) {
            System.out.println("ID inválido!");
            return -1;
        }
        return id - 1;
    }

    public static boolean existeUsuario() {
        if (totalUsuarios == 0) {
            System.out.println("Nenhum usuário cadastrado ainda.");
            return false;
        }
        return true;
    }

    public static void imprimirLinha(String[] colunas) {
        System.out.printf("%-5s%-20s%-15s%-30s%n", colunas[0], colunas[1], colunas[2], colunas[3]);
    }

    public static String lerTexto(String mensagem) {
        String texto;
        do {
            System.out.print(mensagem);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo não pode ficar vazio.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    // Lê a linha inteira e converte, assim não dá erro se digitar letra
    public static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = sc.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números!");
            }
        }
    }
}
