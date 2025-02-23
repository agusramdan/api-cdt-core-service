package agus.ramdan.base.client;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface BaseCommandClient<ResultDTO,CreateDTO,UpdateDTO, ID_DTO> {

    @PostMapping
    ResponseEntity<ResultDTO> createCustomer(CreateDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<ResultDTO> updateCustomer(@PathVariable("id") ID_DTO id, @RequestBody UpdateDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCustomer(@PathVariable("id") ID_DTO id);

}
