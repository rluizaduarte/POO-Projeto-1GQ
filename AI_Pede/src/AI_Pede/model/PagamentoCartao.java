// --- Arquivo: model/PagamentoCartao.java ---
package AI_Pede.model;

public class PagamentoCartao extends Pagamento {
    private String tipo; // "Crédito" ou "Débito"

    public PagamentoCartao(double valor, String tipo) {
        super(valor);
        this.tipo = tipo;
    }

    @Override
    public String getDetalhes() {
        return "Pagamento via Cartão de " + this.tipo;
    }
}