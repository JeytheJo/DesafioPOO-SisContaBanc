package exceptions;

/**
 * Exceção lançada quando uma tentativa de saque excede o saldo disponível.
 *
 * UNCHECKED (extends RuntimeException) — Justificativa:
 * Saldo insuficiente é um erro de lógica de uso: o chamador deveria verificar
 * o saldo antes de sacar. Não faz sentido obrigar TODOS os pontos do sistema
 * a declarar "throws" para algo que, em condições normais, não deveria ocorrer.
 * Segue o mesmo padrão de ArithmeticException e IndexOutOfBoundsException da JDK.
 */
public class SaldoInsuficienteException extends RuntimeException {

    private final double saldoAtual;
    private final double valorSolicitado;

    public SaldoInsuficienteException(double saldoAtual, double valorSolicitado) {
        super(String.format(
            "Saldo insuficiente. Solicitado: R$ %.2f | Disponível: R$ %.2f",
            valorSolicitado, saldoAtual
        ));
        this.saldoAtual = saldoAtual;
        this.valorSolicitado = valorSolicitado;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public double getValorSolicitado() {
        return valorSolicitado;
    }
}
