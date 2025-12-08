/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.controller;
import com.tienda_vm.domain.Sucursal;
import com.tienda_vm.service.SucursalService;
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
@RequestMapping("/sucursal")
public class SucursalController {
  
    @Autowired
    private SucursalService sucursalService;
    
    @Autowired
    private MessageSource messageSource;
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var sucursales = sucursalService.getSucursales(true);
        model.addAttribute("sucursales", sucursales);
        model.addAttribute("totalSucursales", sucursales.size());
        return "/sucursal/listado";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid Sucursal sucursal, RedirectAttributes redirectAttributes) {
        sucursalService.save(sucursal);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/sucursal/listado";
    }
    
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idSucursal, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        try {
            sucursalService.delete(idSucursal);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "sucursal.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "sucursal.error02";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "sucursal.error03";
        }
        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/sucursal/listado";
    }
    
    @GetMapping("/modificar/{idSucursal}")
    public String modificar(@PathVariable("idSucursal") Integer idSucursal,
            RedirectAttributes redirectAttributes, Model model) {
        Optional<Sucursal> sucursalOpt = Optional.ofNullable(sucursalService.getSucursal(idSucursal));
        if (sucursalOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error",
                    messageSource.getMessage("sucursal.error01", null, Locale.getDefault()));
            return "redirect:/sucursal/listado";
        }
        model.addAttribute("sucursal", sucursalOpt.get());
        return "/sucursal/modifica";
    }
}