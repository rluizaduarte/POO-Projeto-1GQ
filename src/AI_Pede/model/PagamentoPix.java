package AI_Pede.model;

public class PagamentoPix extends Pagamento {
    public PagamentoPix(double valor) {
        super(valor);
    }

    @Override
    public String getDetalhes() {
        return "Pagamento via PIX";
    }
}