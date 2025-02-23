package agus.ramdan.base.controller;

import agus.ramdan.base.service.BaseCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
public interface BaseCommandController<ResultDTO,CreateDTO,UpdateDTO, ID_DTO>{

    BaseCommandService<ResultDTO,CreateDTO,UpdateDTO, ID_DTO> getService();

    @PostMapping
    @Operation(summary = "Create new data")
    @ApiResponse(responseCode = "201", description = "Data created successfully")
    default ResponseEntity<ResultDTO> createCustomer(@Valid @RequestBody CreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(getService().commandCreate(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update data details (partial update supported)")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully")
    default ResponseEntity<ResultDTO> updateCustomer(@PathVariable ID_DTO idDTO, @Valid @RequestBody UpdateDTO dto) {
        return ResponseEntity.ok(getService().commandUpdate(idDTO,dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer")
    @ApiResponse(responseCode = "204", description = "Customer deleted successfully")
    default ResponseEntity<Void> deleteCustomer(@PathVariable ID_DTO idDTO) {
        getService().commandDelete(idDTO);
        return ResponseEntity.noContent().build();
    }
}
