package agus.ramdan.cdt.core.master.persistence.domain;

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

        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_branch_ktp ON cdt_branch (code) WHERE deleted_at IS NULL;");

        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_customer_ktp ON cdt_customer (ktp) WHERE deleted_at IS NULL;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_customer_email ON cdt_customer (email) WHERE deleted_at IS NULL;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_customer_msidn ON cdt_customer (msidn) WHERE deleted_at IS NULL;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_customer_npwp ON cdt_customer (npwp) WHERE deleted_at IS NULL;");

        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_ktp ON cdt_customer_crew (ktp) WHERE deleted_at IS NULL and ktp is not NULL and ktp <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_npwp ON cdt_customer_crew (npwp) WHERE deleted_at IS NULL and npwp is not NULL and npwp <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_user_id ON cdt_customer_crew (user_id) WHERE deleted_at IS NULL and user_id is not NULL ;");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_username ON cdt_customer_crew (username) WHERE deleted_at IS NULL and username is not NULL and username <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_email ON cdt_customer_crew (email) WHERE deleted_at IS NULL and email is not NULL and email <> '';");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_cdt_customer_crew_msidn ON cdt_customer_crew (msidn) WHERE deleted_at IS NULL and msidn is not NULL and msidn <> '';");
    }
}
