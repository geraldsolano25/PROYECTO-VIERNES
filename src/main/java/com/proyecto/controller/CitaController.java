/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.controller;
import com.proyecto.domain.Cita;
import com.proyecto.service.CitaService;
import com.proyecto.service.ClienteService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller 
@RequestMapping("/cita")
public class CitaController {
  
    private final CitaService citaService;
    private final ClienteService clienteService;
    private final MessageSource messageSource;

    @Autowired
    public CitaController(CitaService citaService, ClienteService clienteService, MessageSource messageSource) {
        this.citaService = citaService;
        this.clienteService = clienteService;
        this.messageSource = messageSource;
    }
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var citas = citaService.getCitas();
        model.addAttribute("citas", citas);
        model.addAttribute("totalCitas", citas.size());

        // AGREGAR ESTA LÍNEA:
        var clientes = clienteService.getClientes();
        model.addAttribute("clientes", clientes);  // Agregar clientes al modelo para el modal

        return "/cita/listado";
    }
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        // OBTENER LOS CLIENTES PARA EL COMBO
        var clientes = clienteService.getClientes();
        model.addAttribute("cita", new Cita());
        model.addAttribute("clientes", clientes);  // Agregar clientes al modelo
        return "/cita/modifica";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid Cita cita, RedirectAttributes redirectAttributes) {
        citaService.save(cita);
        redirectAttributes.addFlashAttribute("todoOk",
            messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/cita/listado";
    }
    
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long idCita, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        try {
            citaService.delete(idCita);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "cita.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "cita.error02";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "cita.error03";
        }
        redirectAttributes.addFlashAttribute(titulo,
            messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/cita/listado";
    }
    
    @GetMapping("/modificar/{idCita}")
    public String modificar(@PathVariable("idCita") Long idCita,
            RedirectAttributes redirectAttributes, Model model) {
        Optional<Cita> citaOpt = citaService.getCitaOptional(idCita);
        if(citaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                messageSource.getMessage("cita.error01", null, Locale.getDefault()));
            return "redirect:/cita/listado";
        }
        
        // OBTENER LOS CLIENTES PARA EL COMBO
        var clientes = clienteService.getClientes();
        model.addAttribute("cita", citaOpt.get());
        model.addAttribute("clientes", clientes);  // Agregar clientes al modelo
        return "/cita/modifica";
    }
}