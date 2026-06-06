package contas;

import exceptions.OperacaoInvalidaException;
import exceptions.SaldoInsuficienteException;
import interfaces.Tributavel;

import java.time.LocalDate;

/**
 * Conta Poupança.
 * - Rendimento: 0,5% ao mês sobre o saldo
 * - Imposto: 22,5% sobre o rendimento
 * - Restrição: mínimo 30 dias entre saques (simplificado)
 */
public class ContaPoupanca extends ContaBancaria implements Tributavel {

    private static final double TAXA_RENDIMENTO = 0.005;   // 0,5% ao mês
    private static final double TAXA_IMPOSTO    = 0.225;   // 22,5% sobre rendimento
    private static final int    DIAS_MIN_SAQUE  = 30;

    private LocalDate ultimoSaque;

    public ContaPoupanca(String numero, String titular, double saldo) {
        super(numero, titular, saldo);
        this.ultimoSaque = null;
    }

    /**
     * Rendimento = 0,5% ao mês sobre o saldo atual.
     */
    @Override
    public double calcularRendimento() {
        return saldo * TAXA_RENDIMENTO;
    }

    /**
     * Saque com restrição de 30 dias entre saques.
     */
    @Override
    public void sacar(double valor) throws OperacaoInvalidaException {
        verificarContaAtiva();
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        if (ultimoSaque != null) {
            long diasDesdeUltimoSaque = java.time.temporal.ChronoUnit.DAYS.between(ultimoSaque, LocalDate.now());
            if (diasDesdeUltimoSaque < DIAS_MIN_SAQUE) {
                throw new OperacaoInvalidaException(numero,
                    String.format("necessário aguardar %d dia(s) para o próximo saque.",
                        DIAS_MIN_SAQUE - diasDesdeUltimoSaque));
            }
        }
        if (valor > saldo) {
            throw new SaldoInsuficienteException(saldo, valor);
        }
        this.saldo -= valor;
        this.ultimoSaque = LocalDate.now();
        System.out.printf("[%s] Saque de R$ %.2f realizado. Novo saldo: R$ %.2f%n",
                numero, valor, saldo);
    }

    /**
     * Imposto = 22,5% sobre o rendimento mensal.
     */
    @Override
    public double calcularImposto() {
        return calcularRendimento() * TAXA_IMPOSTO;
    }

    public LocalDate getUltimoSaque() {
        return ultimoSaque;
    }
}
