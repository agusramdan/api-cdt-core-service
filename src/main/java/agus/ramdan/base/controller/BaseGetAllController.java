package agus.ramdan.base.controller;

import agus.ramdan.base.exception.Errors;
import agus.ramdan.base.utils.BaseSpecifications;
import agus.ramdan.base.utils.BaseSpecificationsBuilder;
import agus.ramdan.base.utils.OffsetBasedPageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface BaseGetAllController<T, DTO> {

    JpaSpecificationExecutor<T> getRepository();

    DTO convert(T entity);

    @GetMapping("")
    @Operation(summary = "Get All")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema()))),
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content(schema = @Schema(implementation = Errors.class))),
            @ApiResponse(responseCode = "400", description = "Invalid tag value",
                    content = @Content(schema = @Schema(implementation = Errors.class)))})
    default ResponseEntity<List<DTO>> getAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "25") int limit,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "ids", required = false) String ids
    ) {
        val builder = new BaseSpecificationsBuilder<T>();
        if (StringUtils.hasText(ids)) {
            val list = Arrays.stream(ids.split(","))
                    .map(String::trim) // Menghapus spasi di sekitar angka
                    .map(Long::parseLong) // Mengonversi ke Long
                    .collect(Collectors.toList());
            builder.ids_in(list);
            if (!list.isEmpty()) {
                limit = Math.max(list.size(), limit);
            }
        }
        builder.withSearch(search);
        val spec = builder.build(BaseSpecifications::new);
        Sort sort = null;
        if (StringUtils.hasText(order)) {
            List<Sort.Order> orders = Arrays.stream(order.split(","))
                    .map(o -> {
                        String[] orderParts = o.split(":");
                        String fieldName = orderParts[0];
                        Sort.Direction direction = Sort.Direction.fromString(orderParts.length > 1 ? orderParts[1] : "ASC");
                        return new Sort.Order(direction, fieldName);
                    })
                    .collect(Collectors.toList());
            sort = Sort.by(orders);
        }else {
            sort = Sort.by(Sort.Direction.DESC, "createdOn");
        }
        val pageable = new OffsetBasedPageRequest(offset, limit, sort);
        val page = getRepository().findAll(spec, pageable);
        if (page.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        val content = page.getContent().stream().map(this::convert).collect(Collectors.toList());
        return ResponseEntity.ok(content);
    }
}
