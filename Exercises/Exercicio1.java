/**
 * Exercício 1 - Missionarios e Canibais
 */
import java.util.*;

public class Exercicio1 {

    public static final int QUANT_MISS = 3, QUANT_CAN = 3;

    static class Estado {
        int missE, missD;
        int canE, canD;
        boolean barcoNaEsquerda;

        public Estado(int missE, int missD, int canE, int canD, boolean barcoNaEsquerda) {
            this.missE = missE;
            this.missD = missD;
            this.canD = canD;
            this.canE = canE;
            this.barcoNaEsquerda = barcoNaEsquerda;
        }

        boolean eValido() {
            if (this.missE < 0 || this.canE < 0 || this.missE > QUANT_MISS
                    || this.canE > QUANT_CAN) {
                return false;
            }
            if (this.missE < this.canE && this.missE != 0) {
                return false;
            }
            if (this.missD < this.canD && this.missD != 0) {
                return false;
            }
            if (this.missE + this.canE + this.missD + this.canD != QUANT_MISS
                    + QUANT_CAN) {
                throw new IllegalStateException("Quantidade de missionários e canibais inválida.");
            }
            return true;
        }

        boolean eObjetivo() {
            return this.missE == 0 && this.canE == 0 && !barcoNaEsquerda;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Estado) {
                Estado estado = (Estado) obj;
                return this.missE == estado.missE && this.canE == estado.canE
                        && this.barcoNaEsquerda == estado.barcoNaEsquerda && this.missD == estado.missD
                        && this.canD == estado.canD;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(missE, canE, barcoNaEsquerda, missD, canD);
        }

        @Override
        public String toString() {
            return "Missionarios = " + missE + ", Canibais = " + canE + ", Missionarios D = " + missD
                    + ", Canibais D = " + canD + ", Barco = " + (barcoNaEsquerda ? "Esquerda" : "Direita");
        }
    }

    static Queue<List<Estado>> buscarSolucao() {
        Set<Estado> visitados = new HashSet<>();

        Estado inicial = new Estado(QUANT_MISS, 0, QUANT_CAN, 0, true);
        List<Estado> caminhoInicial = new ArrayList<>();
        caminhoInicial.add(inicial);

        Queue<List<Estado>> resultado = new LinkedList<>();
        Queue<List<Estado>> fronteira = new LinkedList<>();
        fronteira.add(caminhoInicial);

        while (!fronteira.isEmpty()) {
            List<Estado> caminho = fronteira.poll();
            Estado estadoAtual = caminho.get(caminho.size() - 1);
            System.out.println("Visitando: " + estadoAtual);
            visitados.add(estadoAtual);

            if (estadoAtual.eObjetivo()) {
                resultado.add(caminho);
                System.out.println("Solução encontrada.");
                continue;
            }

            List<Estado> proximosEstados = gerarEstadosValidos(estadoAtual, visitados);
            for (Estado proximoEstado : proximosEstados) {
                List<Estado> novoCaminho = new ArrayList<>(caminho);
                novoCaminho.add(proximoEstado);
                fronteira.add(novoCaminho);
            }
        }

        return resultado;
    }

    static List<Estado> gerarEstadosValidos(Estado estado, Set<Estado> visitados) {
        List<Estado> proximosEstados = new ArrayList<>();

        if (estado.barcoNaEsquerda) {
            proximosEstados.add(new Estado(estado.missE - 2, estado.missD + 2, estado.canE, estado.canD, false));
            proximosEstados.add(new Estado(estado.missE - 1, estado.missD + 1, estado.canE, estado.canD, false));
            proximosEstados
                    .add(new Estado(estado.missE - 1, estado.missD + 1, estado.canE - 1, estado.canD + 1, false));
            proximosEstados.add(new Estado(estado.missE, estado.missD, estado.canE - 1, estado.canD + 1, false));
            proximosEstados.add(new Estado(estado.missE, estado.missD, estado.canE - 2, estado.canD + 2, false));
        } else {
            proximosEstados.add(new Estado(estado.missE + 2, estado.missD - 2, estado.canE, estado.canD, true));
            proximosEstados.add(new Estado(estado.missE + 1, estado.missD - 1, estado.canE, estado.canD, true));
            proximosEstados.add(new Estado(estado.missE + 1, estado.missD - 1, estado.canE + 1, estado.canD - 1, true));
            proximosEstados.add(new Estado(estado.missE, estado.missD, estado.canE + 1, estado.canD - 1, true));
            proximosEstados.add(new Estado(estado.missE, estado.missD, estado.canE + 2, estado.canD - 2, true));
        }

        proximosEstados.removeIf(visitados::contains);
        proximosEstados.removeIf(estadoAtual -> !estadoAtual.eValido());

        return proximosEstados;
    }

    public static void main(String[] args) {
        Queue<List<Estado>> solucao = buscarSolucao();

        if (solucao.isEmpty()) {
            System.out.println("Não foi encontrada uma solução.");
        } else {
            for (List<Estado> caminho : solucao) {
                System.err.println("\nSolução encontrada:");
                int nrEstado = 1;
                for (Estado estado : caminho) {
                    String barco = estado.barcoNaEsquerda ? "Esquerda" : "Direita";
                    System.out.println("Estado " + nrEstado + ":\t" + estado);
                    nrEstado++;
                }
            }
        }
    }
}
