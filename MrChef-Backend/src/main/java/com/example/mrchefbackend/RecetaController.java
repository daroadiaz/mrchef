package com.example.mrchefbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    @Autowired
    private RecetaRepository recetaRepository;

    @PostMapping("/crear")
    public ResponseEntity<String> crearReceta(@RequestBody RecetaDTO recetaDTO) {
        try {
            // Convertir DTO a entidad
            Receta receta = new Receta();
            receta.setTitulo(recetaDTO.getTitulo());
            receta.setDescripcion(recetaDTO.getDescripcion());
            receta.setIngredientes(recetaDTO.getIngredientes());
            receta.setPreparacion(recetaDTO.getPreparacion());
            
            // Guardar en base de datos
            recetaRepository.save(receta);
            
            return ResponseEntity.ok("Receta creada con éxito. ID: " + receta.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la receta: " + e.getMessage());
        }
    }
    
    @GetMapping("/listar")
    public Iterable<Receta> listarRecetas() {
        return recetaRepository.findAll();
    }
}