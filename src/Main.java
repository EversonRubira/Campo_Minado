import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao Campo Minado!");

        System.out.print("Digite o número de linhas: ");
        int linhas = scanner.nextInt();

        System.out.print("Digite o número de colunas: ");
        int colunas = scanner.nextInt();

        System.out.print("Digite o número de minas: ");
        int numMinas = scanner.nextInt();

        Tabuleiro tabuleiro = new Tabuleiro(linhas, colunas, numMinas);
        boolean fimDeJogo = false;

        while (!fimDeJogo) {
            tabuleiro.imprimirTabuleiro();

            System.out.println("Escolha uma ação:");
            System.out.println("1 - Revelar casa");
            System.out.println("2 - Marcar/Desmarcar casa");
            int acao = scanner.nextInt();

            System.out.print("Digite a linha: ");
            int x = scanner.nextInt();
            System.out.print("Digite a coluna: ");
            int y = scanner.nextInt();

            if (acao == 1) {
                fimDeJogo = tabuleiro.revelarCasa(x, y);
                if (fimDeJogo) {
                    System.out.println("Você perdeu! Uma mina explodiu.");
                    tabuleiro.imprimirTabuleiro();
                    break;
                }
            } else if (acao == 2) {
                tabuleiro.marcarCasa(x, y);
            }

            if (tabuleiro.verificarVitoria()) {
                System.out.println("Parabéns! Você venceu!");
                tabuleiro.imprimirTabuleiro();
                fimDeJogo = true;
            }
        }

        scanner.close();
    }
}