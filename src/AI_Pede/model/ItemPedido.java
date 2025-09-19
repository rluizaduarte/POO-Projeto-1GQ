package AI_Pede.model;

public class ItemPedido {
    private ItemCardapio item;
    private int quantidade;

    public ItemPedido(ItemCardapio item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    public void adicionarQuantidade(int adicional) {
        if (adicional > 0) {
            this.quantidade += adicional;
        }
    }

    public ItemCardapio getItem() { return item; }
    public int getQuantidade() { return quantidade; }
    public double getSubtotal() { return item.getPrecoVenda() * quantidade; }
    public double getCustoTotalItem() { return item.getCusto() * quantidade; }

    @Override
    public String toString() {
        return String.format("%dx %s", quantidade, item.getNome());
    }
}