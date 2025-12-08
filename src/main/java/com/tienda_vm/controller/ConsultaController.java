
package com.tienda_vm.controller;
import com.tienda_vm.service.CategoriaService;
import com.tienda_vm.service.ProductoService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller 
@RequestMapping("/consultas")
public class ConsultaController {
    
    
    private final ProductoService productoService;

    public ConsultaController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    
    
    @GetMapping("/listado")
    public String listado(Model model)
    {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos",productos);
        return "/consultas/listado";
    }
 
    @PostMapping("/consultaDerivada")
    public String consultaDerivada(
            @RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model)
    {
        var productos = productoService.consultaDerivada(precioInf,precioSup);
        model.addAttribute("productos",productos);
        model.addAttribute("precioInf",precioInf);
        model.addAttribute("precioSup",precioSup);
        return "/consultas/listado";
    }
    
     @PostMapping("/consultaJPQL")
    public String consultaJPQL(
            @RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model)
    {
        var productos = productoService.consultaJPQL(precioInf,precioSup);
        model.addAttribute("productos",productos);
        model.addAttribute("precioInf",precioInf);
        model.addAttribute("precioSup",precioSup);
        return "/consultas/listado";
    }
    
      @PostMapping("/consultaSQL")
    public String consultaSQL(
            @RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model)
    {
        var productos = productoService.consultaSQL(precioInf,precioSup);
        model.addAttribute("productos",productos);
        model.addAttribute("precioInf",precioInf);
        model.addAttribute("precioSup",precioSup);
        return "/consultas/listado";
    }
    
    @PostMapping("/consultaPorDescripcion")
    public String consultaPorDescripcion(
            @RequestParam() String descripcion,
            Model model)
    {
        var productos = productoService.consultaPorDescripcion(descripcion);
        model.addAttribute("productos",productos);
        model.addAttribute("descripcion",descripcion);
        return "/consultas/listado";
    }
}
