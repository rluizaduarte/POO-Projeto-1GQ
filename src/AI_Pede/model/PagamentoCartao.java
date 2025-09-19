// --- Arquivo: model/PagamentoCartao.java ---
package AI_Pede.model;

/**
 * Representa um pagamento via cartão.
 * ALTERAÇÃO: A distinção entre Crédito e Débito foi removida para simplificar.
 */
public class PagamentoCartao extends Pagamento {

    public PagamentoCartao(double valor) {
        super(valor);
    }

    @Override
    public String getDetalhes() {
        // A descrição agora é genérica.
        return "Pagamento via Cartão";
    }
}