package org.example.arquivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * DIA 30 - Escrita de Arquivos em JAVA (BufferedWriter)
 * Lê o inventário de hosts (hosts.csv), classifica a criticidade de cada
 * host pelo sistema operacional e grava o resultado em inventario_atualizado.csv.
 *
 * Desafios resolvidos:
 *  1 - Coluna status: Windows = Online, Linux = Em Monitoramento
 *  2 - Contador de hosts Windows, Linux e macOS
 *  3 - Segundo arquivo (hosts_criticos.csv) só com criticidade Alta
 *  4 - Coluna data_auditoria com a data em que o relatório foi gerado
 *  5 - Coluna risco (1 a 10) conforme a criticidade (integração NetSentinel)
 */
public class AtualizaInventario {

    static final String ARQUIVO_ENTRADA = "hosts.csv";
    static final String ARQUIVO_SAIDA = "inventario_atualizado.csv";
    static final String ARQUIVO_CRITICOS = "hosts_criticos.csv";

    public static void main(String[] args) {

        // Desafio 2: contadores de sistemas operacionais
        int totalWindows = 0;
        int totalLinux = 0;
        int totalMac = 0;

        // Desafio 4: data da auditoria no formato dia/mês/ano
        String dataAuditoria = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO_ENTRADA));
            BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO_SAIDA));
            BufferedWriter escritorCriticos = new BufferedWriter(new FileWriter(ARQUIVO_CRITICOS)); // Desafio 3

            // Leitura do cabeçalho original
            String linha = leitor.readLine();

            // Novo cabeçalho com as colunas adicionais
            String novoCabecalho = linha + ",criticidade,status,data_auditoria,risco";
            escritor.write(novoCabecalho);
            escritor.newLine();
            escritorCriticos.write(novoCabecalho);
            escritorCriticos.newLine();

            // Processamento dos registros
            while ((linha = leitor.readLine()) != null) {

                if (linha.isBlank()) {
                    continue; // ignora linhas vazias
                }

                String[] dados = linha.split(",");
                String host = dados[0];
                String ip = dados[1];
                String so = dados[2];

                // Classificação de criticidade
                String criticidade;
                if (so.contains("Kali")) {
                    criticidade = "Alta";
                } else if (so.contains("Ubuntu") || so.contains("Debian") || so.contains("Rocky")) {
                    criticidade = "Baixa";
                } else {
                    criticidade = "Media";
                }

                // Desafio 1 e 2: status e contagem por família de sistema
                String status;
                if (ehWindows(so)) {
                    status = "Online";
                    totalWindows++;
                } else if (ehLinux(so)) {
                    status = "Em Monitoramento";
                    totalLinux++;
                } else if (ehMac(so)) {
                    status = "Sem Status";
                    totalMac++;
                } else {
                    status = "Sem Status";
                }

                // Desafio 5: risco de 1 a 10 conforme a criticidade
                int risco;
                switch (criticidade) {
                    case "Alta":
                        risco = 9;
                        break;
                    case "Media":
                        risco = 5;
                        break;
                    default:
                        risco = 2;
                }

                // Escrita do registro atualizado
                String registro = host + "," + ip + "," + so + "," + criticidade + ","
                        + status + "," + dataAuditoria + "," + risco;
                escritor.write(registro);
                escritor.newLine();

                // Desafio 3: só hosts de criticidade Alta vão para o segundo arquivo
                if (criticidade.equals("Alta")) {
                    escritorCriticos.write(registro);
                    escritorCriticos.newLine();
                }
            }

            // Fechamento dos arquivos
            leitor.close();
            escritor.close();
            escritorCriticos.close();

            System.out.println("Relatorio gerado com sucesso!");
            System.out.println("Arquivos criados: " + ARQUIVO_SAIDA + " e " + ARQUIVO_CRITICOS);
            System.out.println();
            System.out.println("=== Contador de sistemas operacionais ===");
            System.out.println("Windows: " + totalWindows);
            System.out.println("Linux:   " + totalLinux);
            System.out.println("macOS:   " + totalMac);

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static boolean ehWindows(String so) {
        return so.toLowerCase().contains("windows");
    }

    static boolean ehLinux(String so) {
        String s = so.toLowerCase();
        return s.contains("linux") || s.contains("ubuntu") || s.contains("debian")
                || s.contains("rocky") || s.contains("kali") || s.contains("centos")
                || s.contains("fedora");
    }

    static boolean ehMac(String so) {
        String s = so.toLowerCase();
        return s.contains("mac");
    }
}
