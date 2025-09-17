// --- Arquivo: main/TerminalUI.java ---
package AI_Pede.main;

/**
 * Classe utilitária para padronizar e limpar a interface do usuário no terminal.
 */
public class TerminalUI {

    // Cores ANSI para dar ênfase (opcional, mas melhora a leitura)
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";

    /**
     * "Limpa" a tela imprimindo várias linhas em branco.
     */
    public static void limparTela() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }

    /**
     * Exibe um cabeçalho formatado para seções do programa.
     * @param titulo O texto a ser exibido no cabeçalho.
     */
    public static void exibirCabecalho(String titulo) {
        limparTela();
        System.out.println(ANSI_YELLOW + "================================================" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "      " + titulo.toUpperCase() + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "================================================" + ANSI_RESET);
        System.out.println();
    }

    /**
     * Imprime um separador simples para organizar a informação.
     */
    public static void exibirSeparador() {
        System.out.println(ANSI_CYAN + "------------------------------------------------" + ANSI_RESET);
    }

    /**
     * Exibe uma mensagem de erro formatada.
     * @param erro A mensagem de erro a ser exibida.
     */
    public static void exibirErro(String erro) {
        System.out.println(ANSI_RED + "\nERRO: " + erro + ANSI_RESET);
    }

    /**
     * Exibe uma mensagem de sucesso ou informação.
     * @param mensagem A mensagem a ser exibida.
     */
    public static void exibirMensagem(String mensagem) {
        System.out.println("\n" + mensagem);
    }
}