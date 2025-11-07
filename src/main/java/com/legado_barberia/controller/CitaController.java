package com.legado_barberia.controller;
import com.legado_barberia.domain.Cita; import com.legado_barberia.repository.UsuarioRepository; import com.legado_barberia.service.CitaService;
import jakarta.validation.constraints.NotNull; import org.springframework.security.core.Authentication; import org.springframework.validation.annotation.Validated; import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.List;

@Validated @RestController @RequestMapping("/citas")
public class CitaController {
  private record ReservaDTO(@NotNull Long servicioId, @NotNull String inicioIso){}
  private final CitaService citas; private final UsuarioRepository usuarios;
  public CitaController(CitaService c, UsuarioRepository u){citas=c; usuarios=u;}

  @PostMapping public Cita reservar(Authentication auth, @RequestBody ReservaDTO b){
    Long uid=usuarios.findByEmail(auth.getName()).orElseThrow().getId();
    return citas.reservar(uid, b.servicioId(), LocalDateTime.parse(b.inicioIso()));
  }
  @PatchMapping("/{id}/cancelar") public void cancelar(Authentication auth, @PathVariable Long id){
    Long uid=usuarios.findByEmail(auth.getName()).orElseThrow().getId(); citas.cancelar(uid,id);
  }
  @GetMapping("/mias") public List<Cita> mias(Authentication auth){
    Long uid=usuarios.findByEmail(auth.getName()).orElseThrow().getId(); return citas.misCitas(uid);
  }
}
