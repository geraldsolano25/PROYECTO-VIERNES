package com.legado_barberia.controller;
import com.legado_barberia.domain.ServicioBarberia; import com.legado_barberia.service.ServicioBarberiaService;
import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/servicios")
public class ServicioBarberiaController {
  private final ServicioBarberiaService s; public ServicioBarberiaController(ServicioBarberiaService s){this.s=s;}
  @GetMapping public List<ServicioBarberia> listar(){ return s.listar(); }
}
