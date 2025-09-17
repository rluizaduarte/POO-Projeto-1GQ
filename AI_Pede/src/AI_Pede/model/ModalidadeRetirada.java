// --- Arquivo: model/ModalidadeRetirada.java ---
package AI_Pede.model;

/**
 * Representa um pedido para retirada no balcão.
 * Herda de Modalidade e adiciona dados específicos sobre o cliente.
 */
public class ModalidadeRetirada extends Modalidade {
    private String nomeCliente;

    public ModalidadeRetirada(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    @Override
    public String getDescricao() {
        return "Retirada para o cliente: " + nomeCliente;
    }
}