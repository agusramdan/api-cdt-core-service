package agus.ramdan.cdt.core.trx.service.transaction;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.trx.controller.dto.transaction.ServiceTransactionQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.ServiceTransactionMapper;
import agus.ramdan.cdt.core.trx.mapper.ServiceTransactionMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.repository.ServiceTransactionRepository;
import agus.ramdan.cdt.core.trx.persistence.repository.ServiceTransactionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class ServiceTransactionQueryService implements
        BaseQueryEntityService<ServiceTransaction, UUID, ServiceTransactionQueryDTO, String> {

    private final ServiceTransactionRepository repository;
    private final ServiceTransactionMapper mapper;

    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    @Override
    public ServiceTransactionQueryDTO convertOne(ServiceTransaction entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public ServiceTransactionQueryDTO convert(ServiceTransaction entity) {
        return mapper.entityToQueryDto(entity);
    }

}
