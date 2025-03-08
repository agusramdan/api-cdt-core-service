package agus.ramdan.cdt.core.trx.persistence.domain;

public enum TrxTransferStatus {
    PREPARE,
    GATEWAY_TRANSFER, // Saat Transfer ke gateway
    GATEWAY_TIME_OUT,
    REVERSAL,
    REJECT,
    APPROVE,
    ACTIVE,
    INACTIVE,
    EXPIRED,
    DELETED,
    PENDING,
    SUCCESS,
    FAILED,
}
