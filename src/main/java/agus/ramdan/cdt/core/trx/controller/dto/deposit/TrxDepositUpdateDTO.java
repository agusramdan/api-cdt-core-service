package agus.ramdan.cdt.core.trx.controller.dto.deposit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositUpdateDTO {
    private UUID id;
    private String status;
}

