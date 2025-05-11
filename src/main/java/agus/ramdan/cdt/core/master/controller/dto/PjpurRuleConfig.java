package agus.ramdan.cdt.core.master.controller.dto;

public enum PjpurRuleConfig {
    NONE,
    MANDATORY_SUCCESS,
    MANDATORY_EXECUTE;
    public static boolean isNONE(PjpurRuleConfig config) {
        return config == null || config == NONE;
    }
}
