package agus.ramdan.base.controller;

import agus.ramdan.base.exception.Errors;
import agus.ramdan.base.service.BaseQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BaseGetOneController<DTO,DTO_ID>{

    BaseQueryService<DTO,DTO_ID> getService();

    @GetMapping("/{id}")
    @Operation(summary = "Get By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Errors.class)))

    })
    default ResponseEntity<DTO> getById(@PathVariable("id") DTO_ID id){
        return ResponseEntity.ok().body(getService().getByDtoId(id));
    }
}
