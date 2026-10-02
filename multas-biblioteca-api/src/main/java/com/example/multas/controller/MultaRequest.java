package com.example.multas.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MultaRequest {

    @NotBlank(message = "El código de estudiante es obligatorio")
    private String estudianteId;

    @NotBlank(message = "El concepto es obligatorio")
    private String concepto;

    @NotNull(message = "Los días de atraso son obligatorios")
    @Min(value = 1, message = "Los días de atraso deben ser al menos 1")
    private Integer diasAtraso;

    public MultaRequest() {}

    public MultaRequest(String estudianteId, String concepto, Integer diasAtraso) {
        this.estudianteId = estudianteId;
        this.concepto = concepto;
        this.diasAtraso = diasAtraso;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(String estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(Integer diasAtraso) {
        this.diasAtraso = diasAtraso;
    }
}
