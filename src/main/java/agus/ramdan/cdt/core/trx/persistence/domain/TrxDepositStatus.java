package agus.ramdan.cdt.core.trx.persistence.domain;

public enum TrxDepositStatus {
    PREPARE,
    DEPOSIT,
    TRANSFER_IN_PROGRESS,
    TRANSFER_GATEWAY_TIMEOUT,
    TRANSFER_IN_DONE,
    SUCCESS,
    REVERSAL_FROM_CDM,
    REVERSAL_FROM_GATEWAY
}
