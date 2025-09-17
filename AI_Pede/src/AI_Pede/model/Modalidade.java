package AI_Pede.model;

public abstract class Modalidade {

/**
 * CLASSE ABSTRATA: Serve como um "contrato" base para todas as modalidades de pedido.
 * Garante que qualquer tipo de modalidade saberá como se descrever.
 */
    /**
     * MÉTODO ABSTRATO: Força as classes filhas a implementarem sua própria
     * forma de descrição, demonstrando polimorfismo.
     * @return Uma String com os detalhes da modalidade.
     */
    public abstract String getDescricao();
}
