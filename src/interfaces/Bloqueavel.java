package interfaces;

/**
 * Interface que representa a capacidade de uma conta ser bloqueada/desbloqueada.
 * Todas as contas do sistema devem implementá-la.
 */
public interface Bloqueavel {

    /** Bloqueia a conta, impedindo qualquer operação financeira. */
    void bloquear();

    /** Desbloqueia a conta, permitindo operações novamente. */
    void desbloquear();

    /**
     * Verifica se a conta está ativa (não bloqueada).
     *
     * @return true se ativa, false se bloqueada
     */
    boolean isAtiva();
}
