package org.example;

import java.util.Locale;
import java.util.Scanner;

public class Simulador_de_Caixa {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        double saldo = 0.0;
        int opcao;

        do {
            System.out.println("\n===== CAIXA ELETRÔNICO =====");
            System.out.println("1 - Consultar saldo");
            System.out.println("2 - Realizar depósito");
            System.out.println("3 - Realizar saque");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            // Verifica se foi digitado um número inteiro
            if (!scanner.hasNextInt()) {
                System.out.println("Opção inválida");
                scanner.nextLine();
                opcao = 0;
                continue;
            }

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.printf("Saldo atual: R$ %.2f%n", saldo);
                    break;

                case 2:
                    System.out.print("Digite o valor do depósito: R$ ");

                    if (!scanner.hasNextDouble()) {
                        System.out.println("Valor inválido");
                        scanner.nextLine();
                        break;
                    }

                    double deposito = scanner.nextDouble();

                    if (deposito > 0) {
                        saldo += deposito;

                        System.out.printf(
                                "Depósito realizado com sucesso. Saldo: R$ %.2f%n",
                                saldo
                        );
                    } else {
                        System.out.println("Valor inválido");
                    }
                    break;

                case 3:
                    System.out.print("Digite o valor do saque: R$ ");

                    if (!scanner.hasNextDouble()) {
                        System.out.println("Valor inválido");
                        scanner.nextLine();
                        break;
                    }

                    double saque = scanner.nextDouble();

                    if (saque <= 0) {
                        System.out.println("Valor inválido");
                    } else if (saque <= saldo) {
                        saldo -= saque;

                        System.out.printf(
                                "Saque realizado com sucesso. Saldo: R$ %.2f%n",
                                saldo
                        );
                    } else {
                        System.out.println("Saldo insuficiente");
                    }
                    break;

                case 4:
                    System.out.println("Sessão encerrada. Obrigado!");
                    break;

                default:
                    System.out.println("Opção inválida");
            }

        } while (opcao != 4);

        scanner.close();
    }
}