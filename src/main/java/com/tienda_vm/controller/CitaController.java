/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.controller;

import com.tienda_vm.domain.Cita;
import com.tienda_vm.domain.Sucursal;
import com.tienda_vm.service.CitaService;
import com.tienda_vm.service.SucursalService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
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
  
    @Autowired
    private CitaService citaService;
    
    @Autowired
    private SucursalService sucursalService;
    
    @Autowired
    private MessageSource messageSource;
    
    @GetMapping("/listado")
    public String listado(@RequestParam(required = false) String fecha,
                          @RequestParam(required = false) String estado,
                          Model model) {
        
        LocalDate fechaFiltro;
        try {
            fechaFiltro = (fecha != null && !fecha.isEmpty()) ? LocalDate.parse(fecha) : LocalDate.now();
        } catch (Exception e) {
            fechaFiltro = LocalDate.now();
        }
        
        List<Cita> citas;
        
        if (estado != null && !estado.isEmpty()) {
            citas = citaService.getCitasPorEstado(estado);
        } else {
            citas = citaService.getCitasPorFecha(fechaFiltro);
        }
        
        // Ordenar por fechaHora (ascendente)
        citas.sort((c1, c2) -> c1.getFechaHora().compareTo(c2.getFechaHora()));
        
        var sucursalesActivas = sucursalService.getSucursales(true);
        var citasPendientes = citaService.getCitasPendientes();
        var citasHoy = citaService.getCitasHoy();
        
        model.addAttribute("citas", citas);
        model.addAttribute("sucursalesActivas", sucursalesActivas);
        model.addAttribute("fechaFiltro", fechaFiltro);
        model.addAttribute("estadoFiltro", estado);
        model.addAttribute("totalCitas", citas.size());
        model.addAttribute("citasPendientes", citasPendientes.size());
        model.addAttribute("citasHoy", citasHoy.size());
        
        // Para combos de estado
        model.addAttribute("estados", List.of("PENDIENTE", "CONFIRMADA", "COMPLETADA", "CANCELADA"));
        
        return "/cita/listado";
    }
    
   @GetMapping("/formulario")
    public String formulario(Model model) {
        var sucursalesActivas = sucursalService.getSucursales(true);
        model.addAttribute("sucursalesActivas", sucursalesActivas);
        return "/cita/formulario";
    }
    @PostMapping("/guardar")
    public String guardar(@Valid Cita cita, RedirectAttributes redirectAttributes) {
        citaService.save(cita);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/cita/listado";
    }
    
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idCita, RedirectAttributes redirectAttributes) {
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
    public String modificar(@PathVariable("idCita") Integer idCita,
            RedirectAttributes redirectAttributes, Model model) {
        try {
            Cita cita = citaService.getCita(idCita);
            var sucursalesActivas = sucursalService.getSucursales(true);
            
            model.addAttribute("cita", cita);
            model.addAttribute("sucursalesActivas", sucursalesActivas);
            model.addAttribute("estados", List.of("PENDIENTE", "CONFIRMADA", "COMPLETADA", "CANCELADA"));
            
            return "/cita/modifica";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("Error",
                    messageSource.getMessage("cita.error01", null, Locale.getDefault()));
            return "redirect:/cita/listado";
        }
    }
}