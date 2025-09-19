package AI_Pede.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrinho {
    private int numero;
    private final List<ItemPedido> itens;
    private double valorTotal;
    private double custoTotal;
    private Modalidade modalidade;
    private Pagamento pagamento;
    private final LocalDateTime dataHora;
    private String observacao;
    private double percentualDesconto;

    public Carrinho() {
        this.itens = new ArrayList<>();
        this.dataHora = LocalDateTime.now();
        this.observacao = "";
        this.percentualDesconto = 0.0;
        calcularTotais();
    }

    public void adicionarItem(ItemCardapio item, int quantidade) {
        Optional<ItemPedido> itemExistente = itens.stream()
                .filter(i -> i.getItem().getId() == item.getId())
                .findFirst();

        if (itemExistente.isPresent()) {
            itemExistente.get().adicionarQuantidade(quantidade);
        } else {
            this.itens.add(new ItemPedido(item, quantidade));
        }
        calcularTotais();
    }
    
    public void removerItem(int idItemCardapio) {
        boolean removido = this.itens.removeIf(itemPedido -> itemPedido.getItem().getId() == idItemCardapio);
        if (removido) {
            System.out.println("Item removido com sucesso!");
            calcularTotais();
        } else {
            System.out.println("Item não encontrado no carrinho.");
        }
    }

    private void calcularTotais() {
        this.valorTotal = this.itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
        this.custoTotal = this.itens.stream().mapToDouble(ItemPedido::getCustoTotalItem).sum();
    }
    
    public double getValorFinal() {
        return this.valorTotal * (1 - this.percentualDesconto);
    }
    
    public double getCustoTotal() {
        return this.custoTotal;
    }

    public void setObservacao(String observacao) { this.observacao = observacao; }
    public void setPercentualDesconto(double percentual) { this.percentualDesconto = percentual / 100.0; }
    public void setNumero(int numero) { this.numero = numero; }
    public void setModalidade(Modalidade modalidade) { this.modalidade = modalidade; }
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
    
    public List<ItemPedido> getItens() { return itens; }
    public Modalidade getModalidade() { return modalidade; }
    public Pagamento getPagamento() { return pagamento; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Carrinho Finalizado (Pedido Nº: ").append(numero).append(") ---\n");
        if(modalidade != null) sb.append("Modalidade: ").append(modalidade.getDescricao()).append("\n");
        
        sb.append("Itens:\n");
        for (ItemPedido item : itens) {
            sb.append("- ").append(item.toString()).append("\n");
        }

        if (observacao != null && !observacao.isEmpty()) {
            sb.append("Observação: ").append(observacao).append("\n");
        }
        
        sb.append("----------------------------\n");
        sb.append(String.format("Subtotal: R$ %.2f\n", valorTotal));
        if (percentualDesconto > 0) {
            sb.append(String.format("Desconto: %.0f%%\n", percentualDesconto * 100));
            sb.append(String.format("VALOR FINAL: R$ %.2f\n", getValorFinal()));
        } else {
            sb.append(String.format("VALOR TOTAL: R$ %.2f\n", valorTotal));
        }
        if(pagamento != null) sb.append("Forma de Pagamento: ").append(pagamento.getDetalhes()).append("\n");
        sb.append("----------------------------\n");
        return sb.toString();
    }
}