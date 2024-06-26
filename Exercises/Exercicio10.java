import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercicio10 {

    public static final int QUANT_PALITOS = 5;

    static class Nodo {
        int valor;
        List<Nodo> filhos;

        public Nodo(int valor) {
            this.valor = valor;
            this.filhos = new ArrayList<>();
        }
    }

    private static int max(Nodo nodo, int alpha, int beta, int profundidade, boolean inverter, boolean poda) {
        if (nodo.filhos.isEmpty()) {
            System.out.println("Profundidade: " + profundidade + " Valor: " + nodo.valor);
            return nodo.valor;
        }
        int valor = Integer.MIN_VALUE;
        if (inverter) {
            Collections.reverse(nodo.filhos);
        }
        for (int i = 0; i < nodo.filhos.size(); i++) {
            valor = Math.max(valor, min(nodo.filhos.get(i), alpha, beta, profundidade + 1, inverter, poda));
            alpha = Math.max(alpha, valor);
            if (poda) {
                if (alpha >= beta && i < nodo.filhos.size() - 1) {
                    System.out.println("Poda alfa-beta ocorreu no max. Alpha = " + alpha + " Beta = " + beta + " Profundidade = " + profundidade);
                    break;
                }
            }
        }
        System.out.println("Profundidade: " + profundidade + " Alpha: " + alpha);
        nodo.valor = valor;
        return valor;
    }

    private static int min(Nodo nodo, int alpha, int beta, int profundidade, boolean inverter, boolean poda) {
        if (nodo.filhos.isEmpty()) {
            System.out.println("Profundidade: " + profundidade + " Valor: " + nodo.valor);
            return nodo.valor;
        }
        int valor = Integer.MAX_VALUE;
        if (inverter) {
            Collections.reverse(nodo.filhos);
        }
        for (int i = 0; i < nodo.filhos.size(); i++) {
            valor = Math.min(valor, max(nodo.filhos.get(i), alpha, beta, profundidade + 1, inverter, poda));
            beta = Math.min(beta, valor);
            if (poda) {
                if (alpha >= beta && i < nodo.filhos.size() - 1) {
                    System.out.println("Poda alfa-beta ocorreu no min. Alpha = " + alpha + " Beta = " + beta + " Profundidade = " + profundidade);
                    break;
                }
            }
        }
        System.out.println("Profundidade: " + profundidade + " Beta: " + beta);
        nodo.valor = valor;
        return valor;
    }

    private static Nodo criarArvore(int palitos, boolean max) {
        int valor = 0;
        if (palitos == 0) {
            valor = max ? 1 : -1;
        }
        Nodo raiz = new Nodo(valor);
        for (int i = 1; i <= 3; i++) {
            if (palitos - i >= 0) {
                raiz.filhos.add(criarArvore(palitos - i, !max));
            }
        }
        return raiz;
    }

    private static void desenharArvore(Nodo raiz, String prefix) {
        if (raiz == null) return;

        System.out.println(prefix + raiz.valor);
        for (Nodo filho : raiz.filhos) {
            desenharArvore(filho, prefix + "\t");
        }
    }

    public static void main(String[] args) {
        Nodo raiz = criarArvore(QUANT_PALITOS, true);
        
        System.out.println("Árvore de busca:");
        desenharArvore(raiz, "");

        raiz = criarArvore(QUANT_PALITOS, true);

        System.out.println("\nMin-max sem poda:");
        int resultado0 = max(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, false, false);
        System.out.println("Resultado: " + resultado0);
        
        raiz = criarArvore(QUANT_PALITOS, true);

        System.out.println("\nMin-max com poda alfa-beta da esquerda para direita:");
        int resultado1 = max(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, false, true);
        System.out.println("Resultado: " + resultado1);

        raiz = criarArvore(QUANT_PALITOS, true);

        System.out.println("\nMin-max com poda alfa-beta da direita para esquerda:");
        int resultado2 = max(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, true, true);
        System.out.println("Resultado: " + resultado2);
    }
}
