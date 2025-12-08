package com.tienda_vm.controller;

import com.tienda_vm.domain.Cliente;
import com.tienda_vm.service.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {
  
    private final ClienteService clienteService;
    
    @Autowired
    private MessageSource messageSource;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
  @GetMapping("/listado")
public String listado(Model model) {
    var clientes = clienteService.getClientes();
    model.addAttribute("clientes", clientes);  // Asegurar que el nombre sea "clientes"
    model.addAttribute("totalClientes", clientes.size());
    
    return "cliente/listado";
}
    @GetMapping("/nuevo")
    public String nuevo(Cliente cliente) {
        return "cliente/nuevo"; // Quita la barra inicial
    }
    
    
@PostMapping("/guardar")
public String guardar(@Valid Cliente cliente, RedirectAttributes redirectAttributes) {
    clienteService.save(cliente);
    redirectAttributes.addFlashAttribute("todoOk",
        messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
    return "redirect:/cliente/listado";
}
    
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idCliente, // Cambia idCategoria por idCliente     
            RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        try {
            clienteService.delete(idCliente); // Cambia idCategoria por idCliente
        } catch(IllegalArgumentException e) {
            titulo = "error";
            mensaje = "cliente.error01";
        } catch(IllegalStateException e) {
            titulo = "error";
            mensaje = "cliente.error02";
        } catch(Exception e) {
            titulo = "error";
            mensaje = "cliente.error03";
        }
        redirectAttributes.addFlashAttribute(titulo,
            messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/cliente/listado";
    }
    
    @GetMapping("/modificar/{idCliente}") // Cambia idCategoria por idCliente
    public String modificar(@PathVariable("idCliente") Integer idCliente,
            RedirectAttributes redirectAttributes, Model model) {
        Optional<Cliente> clienteOpt = clienteService.getCliente(idCliente); // Cambia getCategoria por getCliente
        if(clienteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("Error",
                messageSource.getMessage("cliente.error01", null, Locale.getDefault()));
            return "redirect:/cliente/listado";
        }
        model.addAttribute("cliente", clienteOpt.get());
        return "cliente/modifica"; // Quita la barra inicial
    }
}