/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.controller;

import com.tienda_vm.domain.Servicio;
import com.tienda_vm.service.ServicioService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicio")
public class ServicioController {
    
    private final ServicioService servicioService;
  
    @Autowired
    private MessageSource messageSource;
    
    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var servicios = servicioService.getServicios(false);
        model.addAttribute("servicios", servicios);
        model.addAttribute("totalServicios", servicios.size());
       
        
        return "/servicio/listado";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid Servicio servicio,
                          @RequestParam MultipartFile imagenFile,
                          RedirectAttributes redirectAttributes) {
        servicioService.save(servicio, imagenFile);
        redirectAttributes.addFlashAttribute("todoOk",
            messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/servicio/listado";
    }
    
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idServicio,
                           RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        
        try {
            servicioService.delete(idServicio);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "servicio.erro01";  // No existe
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "servicio.erro02";  // Datos asociados
        } catch (Exception e) {
            titulo = "error";
            mensaje = "servicio.erro03";  // Error general
        }
        
        redirectAttributes.addFlashAttribute(titulo,
            messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/servicio/listado";
    }
    
    @GetMapping("/modificar/{idServicio}")
    public String modificar(@PathVariable("idServicio") Integer idServicio,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        Optional<Servicio> servicioOpt = servicioService.getServicio(idServicio);
        
        if (servicioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error",
                messageSource.getMessage("servicio.error01", null, Locale.getDefault()));
            return "redirect:/servicio/listado";
        }
        
        model.addAttribute("servicio", servicioOpt.get());
        
        
        return "/servicio/modifica";
    }
}