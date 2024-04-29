/**
 * Exercício 3
 */
import java.util.*;

public class Fazendeiro {

    public static int nrEstado = 0;
    public static List<Estado> visitados = new ArrayList<>();
    public static List<Estado> naoVisitados = new ArrayList<>();

    static class Estado {

        String lobo;
        String ovelha;
        String repolho;
        String fazendeiro;

        public Estado(String fazendeiro, String lobo, String ovelha, String repolho) {
            this.lobo = lobo;
            this.ovelha = ovelha;
            this.repolho = repolho;
            this.fazendeiro = fazendeiro;
        }

        public boolean eValido() {
            if (this.lobo.equals(this.ovelha) && !this.fazendeiro.equals(this.lobo)) {
                return false;
            }
            if (this.ovelha.equals(this.repolho) && !this.fazendeiro.equals(this.ovelha)) {
                return false;
            }
            return true;
        }

        public void printaEstado(int numeroestado) {
            System.out.println("Estado " + Fazendeiro.nrEstado + ": \t" + this.fazendeiro + " | " + this.lobo + " | "
                    + this.ovelha + " | " + this.repolho);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Estado) {
                Estado estado = (Estado) obj;
                return this.fazendeiro.equals(estado.fazendeiro) && this.lobo.equals(estado.lobo)
                        && this.ovelha.equals(estado.ovelha) && this.repolho.equals(estado.repolho);
            }
            return false;
        }
    }

    public static Estado buscarSolucao(Estado estado, Estado estadoFinal) {
        nrEstado++;
        estado.printaEstado(nrEstado);

        if (estado.equals(estadoFinal)) {
            return estado;
        }

        visitados.add(estado);

        List<Estado> proximosEstados = gerarEstadosValidos(estado);
        // TODO
        Collections.reverse(proximosEstados);
        for (Estado proximoEstado : proximosEstados) {
            naoVisitados.add(proximoEstado);
        }

        while (!naoVisitados.isEmpty()) {
            Estado estadoRetirado = naoVisitados.remove(naoVisitados.size() - 1);
            Estado resposta = buscarSolucao(estadoRetirado, estadoFinal);
            if (resposta != null) {
                return resposta;
            }
        }

        return null;
    }

    public static List<Estado> gerarEstadosValidos(Estado estado) {
        List<Estado> proximosEstados = new ArrayList<>();

        String novaMargem = estado.fazendeiro.equals("e") ? "d" : "e";
        proximosEstados.add(new Estado(novaMargem, estado.lobo, estado.ovelha, estado.repolho));
        proximosEstados.add(new Estado(novaMargem, estado.lobo.equals("e") ? "d" : "e", estado.ovelha, estado.repolho));
        proximosEstados.add(new Estado(novaMargem, estado.lobo, estado.ovelha.equals("e") ? "d" : "e", estado.repolho));
        proximosEstados.add(new Estado(novaMargem, estado.lobo, estado.ovelha, estado.repolho.equals("e") ? "d" : "e"));

        proximosEstados.removeIf(visitados::contains);
        proximosEstados.removeIf(naoVisitados::contains);
        proximosEstados.removeIf(estadoAtual -> !estadoAtual.eValido());

        return proximosEstados;
    }

    public static void main(String[] args) {
        Estado estadoInicial = new Estado("e", "e", "e", "e");
        Estado estadoFinal = new Estado("d", "d", "d", "d");

        System.out.println("\t\tF | L | O | R ");

        buscarSolucao(estadoInicial, estadoFinal);
    }

}