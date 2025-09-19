package AI_Pede.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Restaurante {
    private final String nome;
    private final List<ItemCardapio> cardapio;
    private final List<Caixa> historicoDeCaixas;

    public Restaurante(String nome){
        this.nome = nome;
        this.cardapio = new ArrayList<>();
        this.historicoDeCaixas = new ArrayList<>();
    }
    public void adicionarCaixaAoHistorico(Caixa caixa) {
        this.historicoDeCaixas.add(caixa);
    }
    public void adicionarItemCardapio(ItemCardapio item) {
        this.cardapio.add(item);
    }
    public boolean removerItemCardapio(String identificador) {
        Optional<ItemCardapio> itemParaRemover;
        try {
            int id = Integer.parseInt(identificador);
            itemParaRemover = this.cardapio.stream().filter(item -> item.getId() == id).findFirst();
        } catch (NumberFormatException e) {
            itemParaRemover = this.cardapio.stream()
                .filter(item -> item.getNome().equalsIgnoreCase(identificador))
                .findFirst();
        }

        if (itemParaRemover.isPresent()) {
            this.cardapio.remove(itemParaRemover.get());
            return true;
        }
        return false;
    }

    public ItemCardapio buscarItemPorId(int id) {
        return this.cardapio.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }
    
    public String getNome() { return nome; }
    public List<ItemCardapio> getCardapio() { return cardapio; }
    public List<Caixa> getHistoricoDeCaixas() { return historicoDeCaixas; }
}