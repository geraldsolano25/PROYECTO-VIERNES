package com.legado_barberia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class HomeController {
    
  
    // Inyección por constructor (como en tu ejemplo)
    public HomeController() {
     
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("titulo", "Legado Barbería - Inicio");
        return "index";
    }
    
    @GetMapping("/servicios")
    public String servicios() {

        return "servicios";
    }
     @GetMapping("/UbicacionHorarios")
    public String UbicacionHorarios() {

        return "UbicacionHorarios";
    }

    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("titulo", "Nuestros Productos");
        return "productos";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("titulo", "Contacto");
        return "contacto";
    }
}