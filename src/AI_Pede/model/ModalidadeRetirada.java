package AI_Pede.model;

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