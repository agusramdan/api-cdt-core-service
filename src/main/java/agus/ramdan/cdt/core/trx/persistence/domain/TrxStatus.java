package agus.ramdan.cdt.core.trx.persistence.domain;

public enum TrxStatus {
    PREPARE,
    CDM_DEPOSIT,
    // TRANSFER
    TRANSFER,
    TRANSFER_SUCCESS,
    TRANSFER_REJECT,
    TRANSFER_FAILED,
    TRANSFER_TIME_OUT,
    TRANSFER_REVERSAL,

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
