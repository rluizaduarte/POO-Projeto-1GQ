// --- Arquivo: model/RelatorioCompletoDiario.java ---
package AI_Pede.model;

import AI_Pede.main.TerminalUI;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List; // Import necessário
import java.util.Map;

/**
 * ALTERAÇÃO: A assinatura do método 'gerar' foi atualizada para corresponder
 * à nova definição da interface IRelatorio.
 */
public class RelatorioCompletoDiario implements IRelatorio {

    @Override
    public String gerar(List<Caixa> historicoCaixas) { // Assinatura corrigida
        if (historicoCaixas.isEmpty()) {
            return "Nenhum caixa foi fechado ainda para gerar um relatório histórico.";
        }

        StringBuilder sb = new StringBuilder();
        
        // Pergunta A: Dia da semana com maior número de clientes
        sb.append(TerminalUI.ANSI_CYAN + "[DIA DA SEMANA COM MAIOR MOVIMENTO DE CLIENTES (EM MESAS)]\n" + TerminalUI.ANSI_RESET);
        Map<DayOfWeek, Integer> clientesPorDia = new HashMap<>();
        for (Caixa caixa : historicoCaixas) {
            DayOfWeek dia = caixa.getData().getDayOfWeek();
            int clientesNoDia = caixa.getPedidosDoDia().stream()
                .filter(c -> c.getModalidade() instanceof ModalidadeMesa)
                .mapToInt(c -> ((ModalidadeMesa) c.getModalidade()).getQuantidadePessoas())
                .sum();
            clientesPorDia.put(dia, clientesPorDia.getOrDefault(dia, 0) + clientesNoDia);
        }
        
        if (clientesPorDia.values().stream().mapToInt(Integer::intValue).sum() == 0) {
            sb.append("- Nenhum cliente em mesa registrado no período.\n");
        } else {
            Map.Entry<DayOfWeek, Integer> maiorMovimento = clientesPorDia.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get();
            sb.append(String.format("- O dia de maior movimento foi %s, com %d clientes.\n", 
                traduzirDiaDaSemana(maiorMovimento.getKey()), maiorMovimento.getValue()));
        }

        // Outras análises do histórico podem ser adicionadas aqui.

        return sb.toString();
    }

    private String traduzirDiaDaSemana(DayOfWeek day) {
        switch (day) {
            case MONDAY: return "Segunda-feira";
            case TUESDAY: return "Terça-feira";
            case WEDNESDAY: return "Quarta-feira";
            case THURSDAY: return "Quinta-feira";
            case FRIDAY: return "Sexta-feira";
            case SATURDAY: return "Sábado";
            case SUNDAY: return "Domingo";
            default: return "";
        }
    }
}