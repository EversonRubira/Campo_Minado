import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CampoMinadoGUI {
    private JFrame frame;
    private Tabuleiro tabuleiro;
    private JButton[][] botoes;
    private int linhas;
    private int colunas;
    private int numMinas;

    public CampoMinadoGUI(int linhas, int colunas, int numMinas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.numMinas = numMinas;
        this.tabuleiro = new Tabuleiro(linhas, colunas, numMinas);
        this.botoes = new JButton[linhas][colunas];

        inicializarGUI();
    }

    private void inicializarGUI() {
        frame = new JFrame("Campo Minado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(linhas, colunas));

        // Cria os botões do tabuleiro
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                JButton botao = new JButton();
                botao.setPreferredSize(new Dimension(50, 50));
                final int x = i;
                final int y = j;

                // Clique esquerdo → Revelar casa
                botao.addActionListener(e -> {
                    boolean perdeu = tabuleiro.revelarCasa(x, y);
                    atualizarTabuleiro();

                    if (perdeu) {
                        JOptionPane.showMessageDialog(frame, "Você perdeu! Uma mina explodiu!", "Fim de Jogo", JOptionPane.ERROR_MESSAGE);
                        reiniciarJogo();
                    } else if (tabuleiro.verificarVitoria()) {
                        JOptionPane.showMessageDialog(frame, "Parabéns! Você venceu!", "Vitória", JOptionPane.INFORMATION_MESSAGE);
                        reiniciarJogo();
                    }
                });

                // Clique direito → Marcar/Desmarcar bandeira
                botao.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            tabuleiro.marcarCasa(x, y);
                            atualizarTabuleiro();
                        }
                    }
                });

                botoes[i][j] = botao;
                frame.add(botao);
            }
        }

        frame.pack();
        frame.setLocationRelativeTo(null); // Centraliza a janela
        frame.setVisible(true);
    }

    private void atualizarTabuleiro() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                JButton botao = botoes[i][j];
                if (tabuleiro.isRevelado(i, j)) {
                    botao.setEnabled(false);
                    if (tabuleiro.isMina(i, j)) {
                        botao.setText("💣");
                    } else {
                        int minasVizinhas = tabuleiro.getMinasVizinhas(i, j);
                        if (minasVizinhas > 0) {
                            botao.setText(String.valueOf(minasVizinhas));
                            botao.setForeground(getCorNumero(minasVizinhas));
                        }
                    }
                } else if (tabuleiro.isMarcado(i, j)) {
                    botao.setText("⚑");
                } else {
                    botao.setText("");
                }
            }
        }
    }

    private Color getCorNumero(int num) {
        switch (num) {
            case 1: return Color.BLUE;
            case 2: return Color.GREEN;
            case 3: return Color.RED;
            case 4: return Color.DARK_GRAY;
            case 5: return Color.ORANGE;
            case 6: return Color.CYAN;
            case 7: return Color.MAGENTA;
            case 8: return Color.PINK;
            default: return Color.BLACK;
        }
    }

    private void reiniciarJogo() {
        frame.dispose(); // Fecha a janela atual
        new CampoMinadoGUI(linhas, colunas, numMinas); // Abre um novo jogo
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Menu inicial para escolher dificuldade
            String[] opcoes = {"Fácil (9x9, 10 minas)", "Médio (16x16, 40 minas)", "Difícil (16x30, 99 minas)"};
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    "Escolha a dificuldade:",
                    "Campo Minado",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            switch (escolha) {
                case 0:
                    new CampoMinadoGUI(9, 9, 10);
                    break;
                case 1:
                    new CampoMinadoGUI(16, 16, 40);
                    break;
                case 2:
                    new CampoMinadoGUI(16, 30, 99);
                    break;
                default:
                    System.exit(0);
            }
        });
    }
}
