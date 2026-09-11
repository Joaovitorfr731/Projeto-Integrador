package org.example;

import java.util.Scanner;

public class Desafio {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int vendas = 0;
        double total = 0;
        String continuar;

        do {
            System.out.print("Digite o valor da venda: R$ ");
            double valorVenda = scanner.nextDouble();

            total += valorVenda;
            vendas++;

            System.out.print("Deseja registrar outra venda? (S/N): ");
            continuar = scanner.next();
        } while (continuar.equalsIgnoreCase("S"));

        System.out.println("\nResumo das vendas:");
        System.out.println("Quantidade de vendas: " + vendas);
        System.out.printf("Faturamento total: R$ %.2f%n", total);

        scanner.close();
    }
}
