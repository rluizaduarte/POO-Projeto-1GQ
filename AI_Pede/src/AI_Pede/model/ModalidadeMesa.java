// --- Arquivo: model/ModalidadeMesa.java ---
package AI_Pede.model;

/**
 * Representa um pedido para consumo no restaurante.
 * Herda de Modalidade e adiciona dados específicos sobre a ocupação da mesa.
 */
public class ModalidadeMesa extends Modalidade {
    private int quantidadePessoas;

    public ModalidadeMesa(int quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    @Override
    public String getDescricao() {
        return "Mesa para " + quantidadePessoas + " pessoa(s)";
    }
}