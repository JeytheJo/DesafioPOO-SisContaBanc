package contas;

import exceptions.OperacaoInvalidaException;
import exceptions.SaldoInsuficienteException;
import interfaces.Tributavel;

/**
 * Conta Corrente com suporte a cheque especial.
 * - Rendimento: 0%
 * - Imposto: 0,38% sobre cada saque
 * - Saque: pode usar o limite de cheque especial
 */
public class ContaCorrente extends ContaBancaria implements Tributavel {

    private static final double TAXA_IMPOSTO_SAQUE = 0.0038; // 0,38%
    private static final double LIMITE_CHEQUE_ESPECIAL = 1000.00;

    private double totalSacado; // base acumulada para cálculo de imposto

    public ContaCorrente(String numero, String titular, double saldo) {
        super(numero, titular, saldo);
        this.totalSacado = 0;
    }

    /**
     * Conta Corrente não rende.
     */
    @Override
    public double calcularRendimento() {
        return 0.0;
    }

    /**
     * Saque permite uso do cheque especial (até R$ 1.000,00 além do saldo).
     * Acumula o valor sacado para cálculo de imposto.
     */
    @Override
    public void sacar(double valor) throws OperacaoInvalidaException {
        verificarContaAtiva();
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        double limiteTotal = saldo + LIMITE_CHEQUE_ESPECIAL;
        if (valor > limiteTotal) {
            throw new SaldoInsuficienteException(limiteTotal, valor);
        }
        this.saldo -= valor;
        this.totalSacado += valor;
        System.out.printf("[%s] Saque de R$ %.2f realizado. Novo saldo: R$ %.2f%n",
                numero, valor, saldo);
    }

    /**
     * Imposto = 0,38% sobre o total sacado acumulado.
     */
    @Override
    public double calcularImposto() {
        return totalSacado * TAXA_IMPOSTO_SAQUE;
    }

    public double getLimiteChequeEspecial() {
        return LIMITE_CHEQUE_ESPECIAL;
    }

    public double getTotalSacado() {
        return totalSacado;
    }
}
