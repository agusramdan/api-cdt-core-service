package agus.ramdan.cdt.core.master.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseMasterInitializer {
    private final JdbcTemplate jdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void createPartialUniqueIndex() {
        /**
         * , uniqueConstraints = {
         *         @UniqueConstraint(name = "uc_qrcode_code", columnNames = {"code"})
         * }
         */
        //jdbcTemplate.execute("ALTER TABLE IF EXISTS cdt_trx_pickup DROP COLUMN IF EXISTS cdm_trx_time;");
        jdbcTemplate.execute("DROP INDEX IF EXISTS idx_unique_cdt_branch_ktp;");
        jdbcTemplate.execute("DROP INDEX IF EXISTS idx_cdt_trx_cdm_token_signature;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_branch_code ON cdt_branch (code) WHERE deleted_at IS NULL;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_bank_code ON cdt_bank (code) WHERE deleted_at IS NULL;");

        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_customer_ktp ON cdt_customer (ktp) WHERE deleted_at IS NULL;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_customer_email ON cdt_customer (email) WHERE deleted_at IS NULL;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_customer_msisdn ON cdt_customer (msisdn) WHERE deleted_at IS NULL and msisdn is not NULL and msisdn <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_customer_npwp ON cdt_customer (npwp) WHERE deleted_at IS NULL and npwp is not NULL and npwp <> '';");

        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_ktp ON cdt_customer_crew (ktp) WHERE deleted_at IS NULL and ktp is not NULL and ktp <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_npwp ON cdt_customer_crew (npwp) WHERE deleted_at IS NULL and npwp is not NULL and npwp <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_user_id ON cdt_customer_crew (user_id) WHERE deleted_at IS NULL and user_id is not NULL ;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_username ON cdt_customer_crew (username) WHERE deleted_at IS NULL and username is not NULL and username <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_email ON cdt_customer_crew (email) WHERE deleted_at IS NULL and email is not NULL and email <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_msisdn ON cdt_customer_crew (msisdn) WHERE deleted_at IS NULL and msisdn is not NULL and msisdn <> '';");

        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_vendor_crew_ktp ON cdt_vendor_crew (ktp) WHERE deleted_at IS NULL and ktp is not NULL and ktp <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_vendor_crew_npwp ON cdt_vendor_crew (npwp) WHERE deleted_at IS NULL and npwp is not NULL and npwp <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_vendor_crew_user_id ON cdt_vendor_crew (user_id) WHERE deleted_at IS NULL and user_id is not NULL ;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_vendor_crew_username ON cdt_vendor_crew (username) WHERE deleted_at IS NULL and username is not NULL and username <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_vendor_crew_email ON cdt_vendor_crew (email) WHERE deleted_at IS NULL and email is not NULL and email <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_vendor_crew_msisdn ON cdt_vendor_crew (msisdn) WHERE deleted_at IS NULL and msisdn is not NULL and msisdn <> '';");

        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_location_code ON cdt_service_location (code) WHERE deleted_at IS NULL;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_product_code ON cdt_product (code) WHERE deleted_at IS NULL;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_gateway_code ON cdt_gateway (code) WHERE deleted_at IS NULL;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_machine_code ON cdt_machine (code) WHERE deleted_at IS NULL;");

        jdbcTemplate.execute("ALTER TABLE IF EXISTS cdt_trx_cdm DROP CONSTRAINT IF EXISTS cdt_trx_cdm_status_check;");
        jdbcTemplate.execute("ALTER TABLE IF EXISTS service_trx DROP CONSTRAINT IF EXISTS service_trx_status_check;");

    }
}
