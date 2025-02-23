package agus.ramdan.cdt.core.trx.controller.dto;

import agus.ramdan.base.dto.BankDTO;
import agus.ramdan.base.dto.BranchDTO;

public class BeneficiaryAccountDTO {
    private String beneficiary_id;
    // account information
    private String account_number;
    private String account_name;

    private BankDTO bank;
    private BranchDTO branch;
}
