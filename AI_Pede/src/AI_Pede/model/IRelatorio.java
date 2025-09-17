// --- Arquivo: model/IRelatorio.java ---
package AI_Pede.model;

import java.util.List; // Import necessário

/**
 * ALTERAÇÃO: A assinatura do método 'gerar' foi corrigida para aceitar uma
 * Lista de Caixas, permitindo a análise de um histórico completo em vez de um único dia.
 */
public interface IRelatorio {
    /**
     * Gera um relatório em formato de texto com base no histórico de caixas fechados.
     * @param historicoCaixas A lista de objetos Caixa contendo os dados históricos.
     * @return Uma String formatada com o relatório completo.
     */
    String gerar(List<Caixa> historicoCaixas);
}