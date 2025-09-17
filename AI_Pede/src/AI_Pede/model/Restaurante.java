package AI_Pede.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Restaurante {
    private final String nome;
    private final List<ItemCardapio> cardapio;
    // ALTERADO: O restaurante agora guarda um histórico de caixas.
    private final List<Caixa> historicoDeCaixas;

    public Restaurante(String nome) {
        this.nome = nome;
        this.cardapio = new ArrayList<>();
        this.historicoDeCaixas = new ArrayList<>();
    }
    
    // NOVO MÉTODO: Para adicionar um caixa fechado ao histórico.
    public void adicionarCaixaAoHistorico(Caixa caixa) {
        this.historicoDeCaixas.add(caixa);
    }

    public void adicionarItemCardapio(ItemCardapio item) {
        this.cardapio.add(item);
    }
    
    // ALTERADO: Agora remove por ID ou por nome.
    public boolean removerItemCardapio(String identificador) {
        Optional<ItemCardapio> itemParaRemover;
        try {
            // Tenta remover por ID
            int id = Integer.parseInt(identificador);
            itemParaRemover = this.cardapio.stream().filter(item -> item.getId() == id).findFirst();
        } catch (NumberFormatException e) {
            // Se não for um número, remove por nome (ignorando maiúsculas/minúsculas)
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
    
    // Getters
    public String getNome() { return nome; }
    public List<ItemCardapio> getCardapio() { return cardapio; }
    public List<Caixa> getHistoricoDeCaixas() { return historicoDeCaixas; }
}