package contas;

import exceptions.OperacaoInvalidaException;
import exceptions.SaldoInsuficienteException;
import interfaces.Bloqueavel;

/**
 * Classe abstrata base para todos os tipos de conta bancária.
 * Define estrutura comum e comportamentos compartilhados.
 */
public abstract class ContaBancaria implements Bloqueavel {

    protected final String numero;
    protected final String titular;
    protected double saldo;
    private boolean ativa;

    /**
     * Construtor base. Valida que o titular não seja nulo ou vazio.
     *
     * @param numero  número da conta
     * @param titular nome do titular
     * @param saldo   saldo inicial
     * @throws IllegalArgumentException se titular for nulo ou vazio
     */
    public ContaBancaria(String numero, String titular, double saldo) {
        if (titular == null || titular.trim().isEmpty()) {
            throw new IllegalArgumentException("O titular da conta não pode ser nulo ou vazio.");
        }
        if (saldo < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo.");
        }
        this.numero = numero;
        this.titular = titular.trim();
        this.saldo = saldo;
        this.ativa = true;
    }

    // ── Método abstrato ────────────────────────────────────────────────────────

    /**
     * Calcula o rendimento da conta conforme suas regras específicas.
     * Cada subclasse define sua própria lógica.
     *
     * @return valor do rendimento em reais
     */
    public abstract double calcularRendimento();

    // ── Métodos concretos (R3) ─────────────────────────────────────────────────

    /**
     * Deposita um valor na conta.
     *
     * @param valor valor a depositar (deve ser > 0)
     * @throws OperacaoInvalidaException se a conta estiver bloqueada (Checked)
     * @throws IllegalArgumentException  se o valor for <= 0
     */
    public void depositar(double valor) throws OperacaoInvalidaException {
        verificarContaAtiva();
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }
        this.saldo += valor;
        System.out.printf("[%s] Depósito de R$ %.2f realizado. Novo saldo: R$ %.2f%n",
                numero, valor, saldo);
    }

    /**
     * Saca um valor da conta. Subclasses podem sobrescrever para adicionar
     * regras específicas (ex: cheque especial, limite de saques).
     *
     * @param valor valor a sacar (deve ser > 0)
     * @throws OperacaoInvalidaException  se a conta estiver bloqueada (Checked)
     * @throws SaldoInsuficienteException se não houver saldo suficiente (Unchecked)
     * @throws IllegalArgumentException   se o valor for <= 0
     */
    public void sacar(double valor) throws OperacaoInvalidaException {
        verificarContaAtiva();
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        if (valor > saldo) {
            throw new SaldoInsuficienteException(saldo, valor);
        }
        this.saldo -= valor;
        System.out.printf("[%s] Saque de R$ %.2f realizado. Novo saldo: R$ %.2f%n",
                numero, valor, saldo);
    }

    // ── Bloqueavel (R5) ────────────────────────────────────────────────────────

    @Override
    public void bloquear() {
        this.ativa = false;
        System.out.printf("[%s] Conta bloqueada.%n", numero);
    }

    @Override
    public void desbloquear() {
        this.ativa = true;
        System.out.printf("[%s] Conta desbloqueada.%n", numero);
    }

    @Override
    public boolean isAtiva() {
        return ativa;
    }

    // ── Utilitário interno ─────────────────────────────────────────────────────

    /**
     * Verifica se a conta está ativa antes de qualquer operação.
     * Lança OperacaoInvalidaException (Checked) caso esteja bloqueada.
     */
    protected void verificarContaAtiva() throws OperacaoInvalidaException {
        if (!ativa) {
            throw new OperacaoInvalidaException(numero);
        }
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public String getNumero() { return numero; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }

    @Override
    public String toString() {
        return String.format("[%s | %s | Saldo: R$ %.2f | %s]",
                numero, titular, saldo, ativa ? "ATIVA" : "BLOQUEADA");
    }
}
