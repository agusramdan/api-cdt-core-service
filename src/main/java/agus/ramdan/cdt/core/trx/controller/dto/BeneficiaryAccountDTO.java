package agus.ramdan.cdt.core.trx.controller.dto;

import agus.ramdan.cdt.core.master.controller.dto.BankDTO;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;

public class BeneficiaryAccountDTO {
    private String beneficiary_id;
    // account information
    private String account_number;
    private String account_name;

    private BankDTO bank;
    private BranchDTO branch;
}
