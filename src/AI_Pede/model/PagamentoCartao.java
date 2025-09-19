package AI_Pede.model;
public class PagamentoCartao extends Pagamento {

    public PagamentoCartao(double valor) {
        super(valor);
    }

    @Override
    public String getDetalhes() {
        return "Pagamento via Cartão";
    }
}