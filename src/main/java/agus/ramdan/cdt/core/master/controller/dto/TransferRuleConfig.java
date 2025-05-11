package agus.ramdan.cdt.core.master.controller.dto;

public enum TransferRuleConfig {
    NONE,
    MANDATORY_SUCCESS,
    MANDATORY_EXECUTE;
    public static boolean isNONE(TransferRuleConfig config) {
        return config == null || config == NONE;
    }
}
