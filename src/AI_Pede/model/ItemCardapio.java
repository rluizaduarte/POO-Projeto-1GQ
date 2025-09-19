// --- Arquivo: model/ItemCardapio.java ---
package AI_Pede.model;

public class ItemCardapio {
    private static int proximoId = 1;
    private final int id;
    private final String nome;
    private final String descricao; // NOVO ATRIBUTO
    private final double precoVenda;
    private final double custo;

    public ItemCardapio(String nome, String descricao, double precoVenda, double custo) {
        this.id = proximoId++;
        this.nome = nome;
        this.descricao = descricao;
        this.precoVenda = precoVenda;
        this.custo = custo;
    }

    // Getters...
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPrecoVenda() { return precoVenda; }
    public double getCusto() { return custo; }

    @Override
    public String toString() {
        return String.format("ID: %-3d | %-25s | R$ %5.2f\n          ↳ %s", id, nome, precoVenda, descricao);
    }
}