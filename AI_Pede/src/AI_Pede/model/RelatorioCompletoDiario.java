// --- Arquivo: model/RelatorioCompletoDiario.java ---
package AI_Pede.model;

import AI_Pede.main.TerminalUI;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class RelatorioCompletoDiario implements IRelatorio {

    @Override
    public String gerar(List<Caixa> historicoCaixas) {
        if (historicoCaixas == null || historicoCaixas.isEmpty()) {
            return "Nenhum caixa foi fechado ainda para gerar um relatório histórico.";
        }

        StringBuilder sb = new StringBuilder();

        gerarRelatorioFinanceiroGeral(historicoCaixas, sb);
        gerarRelatorioDiaMaisMovimentado(historicoCaixas, sb);
        gerarRelatorioItensMaisPedidos(historicoCaixas, sb);
        gerarRelatorioModalidadeGeral(historicoCaixas, sb);
        gerarRelatorioPagamentoPorDia(historicoCaixas, sb);
        gerarRelatorioLucroSemanal(historicoCaixas, sb);

        return sb.toString();
    }

    private void gerarRelatorioFinanceiroGeral(List<Caixa> historico, StringBuilder sb) {
        sb.append(TerminalUI.formatarCabecalho("RESUMO FINANCEIRO GERAL"));
        double faturamentoTotal = 0;
        int totalPedidos = 0;
        for (Caixa caixa : historico) {
            faturamentoTotal += caixa.calcularFaturamentoTotal();
            totalPedidos += caixa.getPedidosDoDia().size();
        }
        double ticketMedio = (totalPedidos == 0) ? 0 : faturamentoTotal / totalPedidos;
        sb.append(String.format("- Faturamento Acumulado: R$ %.2f\n", faturamentoTotal));
        sb.append(String.format("- Total de Pedidos no Período: %d\n", totalPedidos));
        sb.append(String.format("- Ticket Médio Geral: R$ %.2f por pedido\n", ticketMedio));
    }

    private void gerarRelatorioDiaMaisMovimentado(List<Caixa> historico, StringBuilder sb) {
        sb.append(TerminalUI.formatarCabecalho("DIA DA SEMANA COM MAIOR FLUXO"));
        Map<DayOfWeek, Integer> clientesPorDia = new HashMap<>();
        for (Caixa caixa : historico) {
            DayOfWeek dia = caixa.getData().getDayOfWeek();
            int clientesNoDia = caixa.getPedidosDoDia().stream()
                    .filter(c -> c.getModalidade() instanceof ModalidadeMesa)
                    .mapToInt(c -> ((ModalidadeMesa) c.getModalidade()).getQuantidadePessoas())
                    .sum();
            clientesPorDia.merge(dia, clientesNoDia, Integer::sum);
        }

        Optional<Map.Entry<DayOfWeek, Integer>> maiorMovimento = clientesPorDia.entrySet().stream().max(Map.Entry.comparingByValue());
        if (maiorMovimento.isPresent() && maiorMovimento.get().getValue() > 0) {
            sb.append(String.format("- O dia de maior movimento foi %s, com um total de %d clientes atendidos.\n",
                    traduzirDiaDaSemana(maiorMovimento.get().getKey()), maiorMovimento.get().getValue()));
        } else {
            sb.append("- Nenhum cliente em mesa registrado no período.\n");
        }
    }

    private void gerarRelatorioItensMaisPedidos(List<Caixa> historico, StringBuilder sb) {
        sb.append(TerminalUI.formatarCabecalho("ITENS MAIS PEDIDOS (GERAL)"));
        Map<String, Integer> contagemItens = new HashMap<>();
        for (Caixa caixa : historico) {
            for (Carrinho c : caixa.getPedidosDoDia()) {
                for (ItemPedido ip : c.getItens()) {
                    contagemItens.merge(ip.getItem().getNome(), ip.getQuantidade(), Integer::sum);
                }
            }
        }

        if (contagemItens.isEmpty()) {
            sb.append("- Nenhum item foi vendido no período.\n");
        } else {
            contagemItens.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(5)
                    .forEach(entry -> sb.append(String.format("- %s: %d unidades\n", entry.getKey(), entry.getValue())));
        }
    }

    private void gerarRelatorioModalidadeGeral(List<Caixa> historico, StringBuilder sb) {
        sb.append(TerminalUI.formatarCabecalho("MODALIDADE MAIS COMUM (GERAL)"));
        long mesas = historico.stream().flatMap(caixa -> caixa.getPedidosDoDia().stream())
                .filter(c -> c.getModalidade() instanceof ModalidadeMesa).count();
        long retiradas = historico.stream().flatMap(caixa -> caixa.getPedidosDoDia().stream())
                .filter(c -> c.getModalidade() instanceof ModalidadeRetirada).count();

        sb.append(String.format("- Pedidos em Mesa: %d\n", mesas));
        sb.append(String.format("- Pedidos para Retirada: %d\n", retiradas));
        if (mesas > retiradas) sb.append("- A modalidade preferida é MESA.\n");
        else if (retiradas > mesas) sb.append("- A modalidade preferida é RETIRADA.\n");
        else if (mesas > 0) sb.append("- Houve um empate entre as modalidades.\n");
    }

    private void gerarRelatorioPagamentoPorDia(List<Caixa> historico, StringBuilder sb) {
        sb.append(TerminalUI.formatarCabecalho("PAGAMENTO MAIS USADO (POR DIA)"));
        for (Caixa caixa : historico) {
            Map<String, Integer> contagemPagamentos = new HashMap<>();
            for (Carrinho c : caixa.getPedidosDoDia()) {
                Pagamento p = c.getPagamento();
                String tipo = "Não definido";
                if (p instanceof PagamentoPix) tipo = "PIX";
                else if (p instanceof PagamentoCartao) tipo = "Cartão";
                else if (p instanceof PagamentoDinheiro) tipo = "Dinheiro";
                contagemPagamentos.merge(tipo, 1, Integer::sum);
            }
            Optional<Map.Entry<String, Integer>> maisUsado = contagemPagamentos.entrySet().stream().max(Map.Entry.comparingByValue());
            if (maisUsado.isPresent()) {
                sb.append(String.format("- No dia %s: %s\n", caixa.getData(), maisUsado.get().getKey()));
            } else {
                sb.append(String.format("- No dia %s: Nenhum pagamento registrado.\n", caixa.getData()));
            }
        }
    }

    private void gerarRelatorioLucroSemanal(List<Caixa> historico, StringBuilder sb) {
        sb.append(TerminalUI.formatarCabecalho("LUCRO MÉDIO SEMANAL"));
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        Map<Integer, Double> lucroPorSemana = new HashMap<>();
        for (Caixa caixa : historico) {
            int semanaDoAno = caixa.getData().get(weekFields.weekOfWeekBasedYear());
            double lucroDoDia = caixa.calcularFaturamentoTotal() - caixa.calcularCustoTotal();
            lucroPorSemana.merge(semanaDoAno, lucroDoDia, Double::sum);
        }

        if (lucroPorSemana.isEmpty()) {
            sb.append("- Não há dados suficientes para calcular o lucro semanal.\n");
        } else {
            double lucroTotal = lucroPorSemana.values().stream().mapToDouble(Double::doubleValue).sum();
            double lucroMedioSemanal = lucroTotal / lucroPorSemana.size();
            sb.append(String.format("- O lucro médio por semana de operação foi de R$ %.2f\n", lucroMedioSemanal));
        }
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