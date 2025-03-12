package agus.ramdan.base.client;


import org.springframework.web.bind.annotation.*;

public interface BaseCommandClient<ResultDTO, CreateDTO, UpdateDTO, ID_DTO> {

    @PostMapping
    ResultDTO createCustomer(CreateDTO dto);

    @PutMapping("/{id}")
    ResultDTO updateCustomer(@PathVariable("id") ID_DTO id, @RequestBody UpdateDTO dto);

    @DeleteMapping("/{id}")
    void deleteCustomer(@PathVariable("id") ID_DTO id);

}
