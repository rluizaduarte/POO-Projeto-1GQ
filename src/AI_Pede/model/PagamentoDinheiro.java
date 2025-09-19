package AI_Pede.model;

public class PagamentoDinheiro extends Pagamento {
    private double valorRecebido;

    public PagamentoDinheiro(double valorFinal, double valorRecebido) {
        super(valorFinal);
        this.valorRecebido = valorRecebido;
    }

    public double getTroco() {
        return valorRecebido - super.valor;
    }

    @Override
    public String getDetalhes() {
        return String.format("Pagamento em Dinheiro (Troco: R$ %.2f)", getTroco());
    }
}