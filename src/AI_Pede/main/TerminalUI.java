package AI_Pede.main;

public class TerminalUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    private static final int LARGURA_LINHA = 60;

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


    public static void exibirCabecalho(String titulo) {
        // A limpeza de tela garante que cada menu comece em uma tela limpa.
        limparTela();
        System.out.println(formatarCabecalho(titulo));
    }

    public static void limparTela() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }

    public static void exibirSeparador() {
        System.out.println(
                "\n" + ANSI_GREEN + "-".repeat(LARGURA_LINHA) + ANSI_RESET + "\n"
        );
    }


    public static void exibirErro(String erro) {
        System.out.println(ANSI_RED + "\nERRO: " + erro + ANSI_RESET);
    }

    public static void exibirMensagem(String mensagem) {
        System.out.println("\n" + mensagem);
    }
}