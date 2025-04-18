package com.example.mrchef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class RecetasController {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @GetMapping("/recetas")
    public String login() {
        return "recetas";
    }

    @GetMapping("recetas/crear")
    public String mostrarFormulario(Model model) {
        // Verificar si hay un token disponible
        if (tokenStore.getToken() == null) {
            model.addAttribute("message", "Por favor inicie sesión para crear recetas");
            return "redirect:/login";
        }
        return "receta";
    }

    @PostMapping("/recetas/crear")
    public String crearReceta(@ModelAttribute RecetaDTO receta, Model model) {
        String url = "http://localhost:8080/recetas/crear";

        // Verificar autenticación
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated() || tokenStore.getToken() == null) {
            model.addAttribute("message", "Sesión expirada. Por favor inicie sesión nuevamente.");
            return "redirect:/login";
        }

        RestTemplate restTemplate = new RestTemplate();
        String token = tokenStore.getToken();

        System.out.println("Token usado para crear receta: " + token);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RecetaDTO recetaBackend = new RecetaDTO();
        recetaBackend.setTitulo(receta.getNombre());
        recetaBackend.setDescripcion(receta.getDescripcion());
        recetaBackend.setIngredientes(receta.getIngredientes());
        recetaBackend.setPreparacion(receta.getInstrucciones());

        HttpEntity<RecetaDTO> entity = new HttpEntity<>(recetaBackend, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class);
            
            model.addAttribute("message", "Receta creada exitosamente: " + response.getBody());
        } catch (Exception e) {
            model.addAttribute("message", "Error al crear la receta: " + e.getMessage());
            System.err.println("Error completo al crear receta: ");
            e.printStackTrace();
        }
        
        return "resultado";
    }
}