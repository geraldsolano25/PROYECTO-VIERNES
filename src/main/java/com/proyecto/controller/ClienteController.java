/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.controller;
import com.proyecto.domain.Cliente;
import com.proyecto.service.ClienteService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/cliente")
public class ClienteController {
  
    private final ClienteService clienteService;
    private final MessageSource messageSource;

    @Autowired
    public ClienteController(ClienteService clienteService, MessageSource messageSource) {
        this.clienteService = clienteService;
        this.messageSource = messageSource;
    }
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var clientes = clienteService.getClientes();
        model.addAttribute("clientes", clientes);
        model.addAttribute("totalClientes", clientes.size());
        return "/cliente/listado";
    }
    
    @GetMapping("/nuevo")
    public String nuevo(Cliente cliente) {
        return "/cliente/modifica";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid Cliente cliente, RedirectAttributes redirectAttributes) {
        clienteService.save(cliente);
        redirectAttributes.addFlashAttribute("todoOk",
            messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/cliente/listado";
    }
    
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long idCliente, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        try {
            clienteService.delete(idCliente);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "cliente.error01"; // Cliente no encontrado
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "cliente.error02"; // No se puede eliminar por datos asociados
        } catch (Exception e) {
            titulo = "error";
            mensaje = "cliente.error03"; // Error general
        }
        redirectAttributes.addFlashAttribute(titulo,
            messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/cliente/listado";
    }
    
    @GetMapping("/modificar/{idCliente}")
    public String modificar(@PathVariable("idCliente") Long idCliente,
            RedirectAttributes redirectAttributes, Model model) {
        Optional<Cliente> clienteOpt = clienteService.getClienteOptional(idCliente);
        if(clienteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                messageSource.getMessage("cliente.error01", null, Locale.getDefault()));
            return "redirect:/cliente/listado";
        }
        model.addAttribute("cliente", clienteOpt.get());
        return "/cliente/modifica";
    }
    
    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) String nombre, Model model) {
        List<Cliente> clientes;
        if (nombre != null && !nombre.trim().isEmpty()) {
            clientes = clienteService.searchByNombre(nombre);
        } else {
            clientes = clienteService.getClientes();
        }
        model.addAttribute("clientes", clientes);
        model.addAttribute("totalClientes", clientes.size());
        return "/cliente/listado";
    }
}