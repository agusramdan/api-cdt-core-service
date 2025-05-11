package agus.ramdan.cdt.core.pjpur.controller.dto.deposit;

import lombok.Data;

/**
 * userid String M Depositor ID
 * username String O Depositor User Name
 * bankcode String M Bank Code
 * bankacc String M Account VA Number
 */
@Data
public class AccountInfoDTO {
    private String userid;
    private String username;
    private String bankcode;
    private String bankacc;
}
