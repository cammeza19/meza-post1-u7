package com.example.multas.controller;

import com.example.multas.model.Multa;
import com.example.multas.service.MultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/multas")
public class MultaController {

    private final MultaService multaService;

    public MultaController(MultaService multaService) {
        this.multaService = multaService;
    }

    @GetMapping
    public ResponseEntity<List<Multa>> listarTodas() {
        return ResponseEntity.ok(multaService.listarTodas());
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Multa>> listarPorEstudiante(@PathVariable String estudianteId) {
        return ResponseEntity.ok(multaService.listarPorEstudiante(estudianteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Multa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(multaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Multa> generar(@Valid @RequestBody MultaRequest request) {
        Multa creada = multaService.generar(request.getEstudianteId(), request.getConcepto(), request.getDiasAtraso());
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<Multa> pagarEnVentanilla(@PathVariable Long id) {
        return ResponseEntity.ok(multaService.pagarEnVentanilla(id));
    }

    // NUEVO ENDPOINT: Pago en línea con pasarela
    @PostMapping("/{id}/pagar-linea")
    public ResponseEntity<Multa> pagarConPasarela(@PathVariable Long id) {
        return ResponseEntity.ok(multaService.pagarConPasarela(id));
    }
}