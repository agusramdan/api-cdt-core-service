package agus.ramdan.cdt.core.trx.persistence.domain;

public enum TrxStatus {
    PREPARE,
    CDM_DEPOSIT,
    GATEWAY_TRANSFER,
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
