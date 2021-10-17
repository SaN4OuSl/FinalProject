package org.example.controller;

import org.example.dto.request.FarmDtoRequest;
import org.example.dto.response.ResponseContainer;
import org.example.entity.Farm;
import org.example.facade.FarmFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/farms")
public class FarmController {
    
    private final FarmFacade farmFacade;
    
    public FarmController(FarmFacade farmFacade) {
        this.farmFacade = farmFacade;
    }
    
    @PostMapping
    private ResponseEntity<ResponseContainer<Boolean>> create(@RequestBody FarmDtoRequest dto) {
        farmFacade.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseContainer<>(true));
    }
    
    @PutMapping("/{id}")
    private ResponseEntity<ResponseContainer<Boolean>> update(@RequestBody FarmDtoRequest dto, @PathVariable Long id) {
        farmFacade.update(dto, id);
        return ResponseEntity.ok(new ResponseContainer<>(true));
    }
    
    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseContainer<Boolean>> delete(@PathVariable Long id) {
        farmFacade.delete(id);
        return ResponseEntity.ok(new ResponseContainer<>(true));
    }
    
    @GetMapping("/{id}")
    private ResponseEntity<ResponseContainer<Farm>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseContainer<>(farmFacade.findById(id)));
    }
    
    @GetMapping
    public ResponseEntity<ResponseContainer<List<Farm>>> findAll() {
        return ResponseEntity.ok(new ResponseContainer<>(farmFacade.findAll()));
    }
}
