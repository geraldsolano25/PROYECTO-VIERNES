package com.proyecto.controller;

import com.proyecto.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class IndexController {
    
    private final ClienteService clienteService;

    @Autowired
    public IndexController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @GetMapping("/")
    public String listado(Model model) {
        // Usamos getClientes() sin parámetro (no existe getClientes(boolean))
        var clientes = clienteService.getClientes();
        model.addAttribute("clientes", clientes);        
        return "/index";
    }
}