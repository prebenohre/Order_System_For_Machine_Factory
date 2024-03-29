package no.kristiania.ordersystemformachinefactory.controller;

import no.kristiania.ordersystemformachinefactory.DTO.AddPartToSubassemblyDto;
import no.kristiania.ordersystemformachinefactory.model.Subassembly;
import no.kristiania.ordersystemformachinefactory.service.SubassemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subassemblies")
public class SubassemblyController {

    private final SubassemblyService subassemblyService;

    @Autowired
    public SubassemblyController(SubassemblyService subassemblyService) {
        this.subassemblyService = subassemblyService;
    }

    @GetMapping
    public List<Subassembly> getAllSubassemblies() {
        return subassemblyService.findAllSubassemblies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subassembly> getSubassemblyById(@PathVariable Long id) {
        return subassemblyService.findSubassemblyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Subassembly createSubassembly(@RequestBody Subassembly subassembly) {
        return subassemblyService.saveSubassembly(subassembly);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subassembly> updateSubassembly(@PathVariable Long id, @RequestBody Subassembly subassembly) {
        return ResponseEntity.ok(subassemblyService.updateSubassembly(id, subassembly));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubassembly(@PathVariable Long id) {
        subassemblyService.deleteSubassembly(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Page<Subassembly>> getSubassembliesByPage(
            @PathVariable int pageNumber,
            @RequestParam(defaultValue = "10") int size) {
        Page<Subassembly> page = subassemblyService.getSubassembliesPageable(pageNumber, size);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/{subassemblyId}/addPart")
    public ResponseEntity<Subassembly> addPartToSubassembly(
            @PathVariable Long subassemblyId,
            @RequestBody AddPartToSubassemblyDto addPartToSubassemblyDto) {
        Subassembly updatedSubassembly = subassemblyService.addPartToSubassembly(subassemblyId, addPartToSubassemblyDto);
        return ResponseEntity.ok(updatedSubassembly);
    }
}
