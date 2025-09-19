package AI_Pede.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Caixa {
    private final LocalDate data;
    private final List<Carrinho> pedidosDoDia;
    private int proximoNumeroPedido = 1;

    public Caixa(LocalDate data) {
        this.data = data;
        this.pedidosDoDia = new ArrayList<>();
    }

    public void adicionarPedido(Carrinho carrinho) {
        carrinho.setNumero(proximoNumeroPedido++);
        this.pedidosDoDia.add(carrinho);
    }

    public double calcularFaturamentoTotal() {
        return this.pedidosDoDia.stream().mapToDouble(Carrinho::getValorFinal).sum();
    }

    public double calcularCustoTotal() {
        return this.pedidosDoDia.stream().mapToDouble(Carrinho::getCustoTotal).sum();
    }
    
    public LocalDate getData() { return data; }
    public List<Carrinho> getPedidosDoDia() { return pedidosDoDia; }
}