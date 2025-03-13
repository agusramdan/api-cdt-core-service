package agus.ramdan.cdt.core.master.service.machine;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.machine.MachineUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.MachineMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.master.persistence.repository.MachineRepository;
import agus.ramdan.cdt.core.master.service.branch.BranchQueryService;
import agus.ramdan.cdt.core.master.service.location.ServiceLocationQueryService;
import agus.ramdan.cdt.core.master.service.vendor.VendorQueryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MachineCommandService implements
        BaseCommandEntityService<Machine, UUID, MachineQueryDTO, MachineCreateDTO, MachineUpdateDTO, String> {

    private final MachineRepository repository;
    private final MachineMapper mapper;
    private final BranchQueryService branchQueryService;
    private final ServiceLocationQueryService serviceLocationQueryService;
    private final VendorQueryService vendorQueryService;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Machine saveCreate(Machine data) {
        return repository.save(data);
    }

    @Override
    public Machine saveUpdate(Machine data) {
        return repository.save(data);
    }

    @Override
    public Machine convertFromCreateDTO(MachineCreateDTO dto) {
        val validations = new ArrayList<ErrorValidation>();
        val entity = mapper.createDtoToEntity(dto);
        // Fetch related Customer entity and set it
        serviceLocationQueryService.relation(dto.getServiceLocationId(), d -> ErrorValidation.add(validations, "Service Location not found", "customer_id", d))
                .or(() -> serviceLocationQueryService.relation(dto.getServiceLocation(), validations, "service_location")).ifPresent(entity::setServiceLocation);
        branchQueryService.relation(dto.getBranchId(), d -> ErrorValidation.add(validations, "Branch not found", "branch_id", d))
                .or(() -> branchQueryService.relation(dto.getBranch(), validations, "branch")).ifPresent(entity::setBranch);
        vendorQueryService.relation(dto.getSupplier(), validations, "supplier").ifPresent(entity::setSupplier);
        vendorQueryService.relation(dto.getMaintenance(), validations, "maintenance").ifPresent(entity::setMaintenance);
        vendorQueryService.relation(dto.getPjpur(), validations, "pjpur").ifPresent(entity::setPjpur);
        BadRequestException.ThrowWhenError("Validation error", validations);
        return entity;
    }

    @Override
    public Machine convertFromUpdateDTO(String id, MachineUpdateDTO dto) {
        val entity = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found"));
        val validations = new ArrayList<ErrorValidation>();
        serviceLocationQueryService.relation(dto.getServiceLocationId(), d -> ErrorValidation.add(validations, "Customer not found", "customer_id", d))
                .or(() -> serviceLocationQueryService.relation(dto.getServiceLocation(), validations, "customer")).ifPresent(entity::setServiceLocation);
        branchQueryService.relation(dto.getBranchId(), d -> ErrorValidation.add(validations, "Branch not found", "branch_id", d))
                .or(() -> branchQueryService.relation(dto.getBranch(), validations, "branch")).ifPresent(entity::setBranch);
        vendorQueryService.relation(dto.getSupplier(), validations, "supplier").ifPresent(entity::setSupplier);
        vendorQueryService.relation(dto.getMaintenance(), validations, "maintenance").ifPresent(entity::setMaintenance);
        vendorQueryService.relation(dto.getPjpur(), validations, "pjpur").ifPresent(entity::setPjpur);
        mapper.updateEntityFromUpdateDto(dto, entity);
        BadRequestException.ThrowWhenError("Validation error", validations);
        return entity;
    }

    @Override
    public MachineQueryDTO convertToResultDTO(Machine entity) {
        return mapper.entityToQueryDto(entity);
    }
}
