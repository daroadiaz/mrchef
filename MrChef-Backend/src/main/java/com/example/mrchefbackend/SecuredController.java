package com.example.mrchefbackend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredController {

    @RequestMapping("recetas/detalles")
    public String detalles(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hello {" + name + "}";
    }
    
}
