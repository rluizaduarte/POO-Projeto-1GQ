// --- Arquivo: main/TerminalUI.java ---
package AI_Pede.main;

/**
 * Classe utilitária para padronizar e limpar a interface do usuário no terminal.
 */
public class TerminalUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    private static final int LARGURA_LINHA = 60;

    /**
     * NOVO MÉTODO: Formata um texto como um cabeçalho e o retorna como String.
     * Isso permite que a formatação seja usada em lugares que não imprimem diretamente no console.
     * @param titulo O texto a ser formatado.
     * @return O cabeçalho formatado como uma String.
     */
    public static String formatarCabecalho(String titulo) {
        String textoTitulo = " " + titulo.toUpperCase() + " ";
        int tamanhoTitulo = textoTitulo.length();

        if (tamanhoTitulo >= LARGURA_LINHA) {
            return "\n" + ANSI_GREEN + textoTitulo + ANSI_RESET + "\n";
        }

        int tamanhoPaddingTotal = LARGURA_LINHA - tamanhoTitulo;
        int paddingEsquerdo = tamanhoPaddingTotal / 2;
        int paddingDireito = tamanhoPaddingTotal - paddingEsquerdo;

        String padEsquerdoStr = "=".repeat(paddingEsquerdo);
        String padDireitoStr = "=".repeat(paddingDireito);

        // O \n no início e no fim garantem o espaçamento correto no relatório
        return "\n" + ANSI_GREEN + padEsquerdoStr + textoTitulo + padDireitoStr + ANSI_RESET + "\n";
    }

    /**
     * Exibe um cabeçalho padronizado (agora usa o método de formatação).
     * @param titulo O texto a ser exibido no cabeçalho.
     */
    public static void exibirCabecalho(String titulo) {
        // A limpeza de tela garante que cada menu comece em uma tela limpa.
        limparTela();
        System.out.println(formatarCabecalho(titulo));
    }

    /**
     * "Limpa" a tela imprimindo várias linhas em branco.
     */
    public static void limparTela() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }

    /**
     * Imprime um separador padronizado.
     */
    public static void exibirSeparador() {
        System.out.println(
                "\n" + ANSI_GREEN + "-".repeat(LARGURA_LINHA) + ANSI_RESET + "\n"
        );
    }

    /**
     * Exibe uma mensagem de erro formatada em Vermelho.
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