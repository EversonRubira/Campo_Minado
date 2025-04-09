import java.util.Random;

public class Tabuleiro {
    private int[][] tabuleiro;  // -1 = mina, 0-8 = número de minas vizinhas
    private boolean[][] revelado; // true = casa revelada
    private boolean[][] marcado;  // true = casa marcada com bandeira
    private int linhas;
    private int colunas;
    private int numMinas;

    public Tabuleiro(int linhas, int colunas, int numMinas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.numMinas = numMinas;
        this.tabuleiro = new int[linhas][colunas];
        this.revelado = new boolean[linhas][colunas];
        this.marcado = new boolean[linhas][colunas];
        inicializarTabuleiro();
        colocarMinas();
        calcularDicas();
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiro[i][j] = 0;
                revelado[i][j] = false;
                marcado[i][j] = false;
            }
        }
    }

    private void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;

        while (minasColocadas < numMinas) {
            int x = random.nextInt(linhas);
            int y = random.nextInt(colunas);

            if (tabuleiro[x][y] != -1) {
                tabuleiro[x][y] = -1; // -1 representa uma mina
                minasColocadas++;
            }
        }
    }

    private void calcularDicas() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (tabuleiro[i][j] == -1) continue; // Ignora minas

                int minasVizinhas = 0;

                // Verifica as 8 casas ao redor
                for (int x = Math.max(0, i - 1); x <= Math.min(linhas - 1, i + 1); x++) {
                    for (int y = Math.max(0, j - 1); y <= Math.min(colunas - 1, j + 1); y++) {
                        if (tabuleiro[x][y] == -1) minasVizinhas++;
                    }
                }

                tabuleiro[i][j] = minasVizinhas;
            }
        }
    }

    public boolean revelarCasa(int x, int y) {
        if (x < 0 || x >= linhas || y < 0 || y >= colunas || revelado[x][y] || marcado[x][y]) {
            return false; // Movimento inválido
        }

        revelado[x][y] = true;

        if (tabuleiro[x][y] == -1) {
            return true; // Perdeu o jogo (clicou na mina)
        }

        // Se for uma casa vazia (0), revela automaticamente as vizinhas
        if (tabuleiro[x][y] == 0) {
            for (int i = Math.max(0, x - 1); i <= Math.min(linhas - 1, x + 1); i++) {
                for (int j = Math.max(0, y - 1); j <= Math.min(colunas - 1, y + 1); j++) {
                    if (!revelado[i][j]) revelarCasa(i, j);
                }
            }
        }

        return false;
    }

    public void marcarCasa(int x, int y) {
        if (!revelado[x][y]) {
            marcado[x][y] = !marcado[x][y]; // Alterna entre marcado/não marcado
        }
    }

    public boolean verificarVitoria() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (tabuleiro[i][j] != -1 && !revelado[i][j]) {
                    return false; // Ainda há casas não reveladas
                }
            }
        }
        return true; // Todas as casas seguras foram reveladas
    }

    public void imprimirTabuleiro() {
        System.out.print("  ");
        for (int j = 0; j < colunas; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < linhas; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < colunas; j++) {
                if (!revelado[i][j]) {
                    System.out.print(marcado[i][j] ? "⚑ " : "■ ");
                } else if (tabuleiro[i][j] == -1) {
                    System.out.print("💣 ");
                } else if (tabuleiro[i][j] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(tabuleiro[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean isRevelado(int x, int y) {
        return revelado[x][y];
    }

    public boolean isMarcado(int x, int y) {
        return marcado[x][y];
    }

    public boolean isMina(int x, int y) {
        return tabuleiro[x][y] == -1;
    }

    public int getMinasVizinhas(int x, int y) {
        return tabuleiro[x][y];
    }

}