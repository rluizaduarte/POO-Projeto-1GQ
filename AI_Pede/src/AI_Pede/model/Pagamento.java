// --- Arquivo: model/Pagamento.java ---
package AI_Pede.model;

/**
 * CLASSE ABSTRATA: Modelo para todas as formas de pagamento.
 * Define o que é comum a todos (o valor) e o que deve ser específico (os detalhes).
 */
public abstract class Pagamento {
    protected double valor;

    public Pagamento(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    /**
     * MÉTODO ABSTRATO: Obriga as classes filhas a detalharem como o pagamento foi feito.
     * @return Uma String com os detalhes do pagamento.
     */
    public abstract String getDetalhes();
}