package org.example.controller;

import org.example.dto.request.FarmDtoRequest;
import org.example.dto.response.ResponseContainer;
import org.example.entity.Farms;
import org.example.facade.FarmsFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/farms")
public class FarmsController {
    
    private final FarmsFacade farmsFacade;
    
    public FarmsController(FarmsFacade farmsFacade) {
        this.farmsFacade = farmsFacade;
    }
    
    @PostMapping
    private ResponseEntity<ResponseContainer<Boolean>> create(@RequestBody FarmDtoRequest dto) {
        farmsFacade.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseContainer<>(true));
    }
    
    @PutMapping("/{id}")
    private ResponseEntity<ResponseContainer<Boolean>> update(@RequestBody FarmDtoRequest dto, @PathVariable Long id) {
        farmsFacade.update(dto, id);
        return ResponseEntity.ok(new ResponseContainer<>(true));
    }
    
    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseContainer<Boolean>> delete(@PathVariable Long id) {
        farmsFacade.delete(id);
        return ResponseEntity.ok(new ResponseContainer<>(true));
    }
    
    @GetMapping("/{id}")
    private ResponseEntity<ResponseContainer<Farms>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseContainer<>(farmsFacade.findById(id)));
    }
    
    @GetMapping
    public ResponseEntity<ResponseContainer<List<Farms>>> findAll() {
        return ResponseEntity.ok(new ResponseContainer<>(farmsFacade.findAll()));
    }
}
