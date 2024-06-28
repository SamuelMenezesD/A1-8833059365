import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessaAlunos {

    public static void main(String[] args) {
        String inputFileName = "alunos.csv";
        String outputFileName = "resumo.csv";
        List<Aluno> listaDeAlunos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String linha;

            br.readLine();

            while ((linha = br.readLine()) != null) {

                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] dados = linha.split(";");

                if (dados.length < 3) {
                    System.out.println("Linha mal formatada: " + linha);
                    continue;
                }
                String matricula = dados[0];
                String nome = dados[1];
                double nota;
                try {
                    nota = Double.parseDouble(dados[2].replace(",", "."));
                } catch (NumberFormatException e) {
                    System.out.println("Nota inválida: " + dados[2]);
                    continue;
                }
                listaDeAlunos.add(new Aluno(matricula, nome, nota));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int quantidadeAlunos = listaDeAlunos.size();
        int aprovados = 0;
        int reprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0.0;

        for (Aluno aluno : listaDeAlunos) {
            double nota = aluno.getNota();
            somaNotas += nota;
            if (nota >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }
            if (nota < menorNota) {
                menorNota = nota;
            }
            if (nota > maiorNota) {
                maiorNota = nota;
            }
        }

        double mediaGeral = somaNotas / quantidadeAlunos;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            bw.write("Quantidade de alunos," + quantidadeAlunos);
            bw.newLine();
            bw.write("Aprovados," + aprovados);
            bw.newLine();
            bw.write("Reprovados," + reprovados);
            bw.newLine();
            bw.write("Menor nota," + menorNota);
            bw.newLine();
            bw.write("Maior nota," + maiorNota);
            bw.newLine();
            bw.write("Média geral," + mediaGeral);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}