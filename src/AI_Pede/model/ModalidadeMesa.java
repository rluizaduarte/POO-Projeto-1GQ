package AI_Pede.model;
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