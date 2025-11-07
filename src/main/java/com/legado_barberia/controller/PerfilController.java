package com.legado_barberia.controller;
import com.legado_barberia.domain.Usuario; import com.legado_barberia.repository.UsuarioRepository; import com.legado_barberia.service.PerfilService;
import jakarta.validation.constraints.NotBlank; import org.springframework.security.core.Authentication; import org.springframework.validation.annotation.Validated; import org.springframework.web.bind.annotation.*;

@Validated @RestController @RequestMapping("/perfil")
public class PerfilController {
  private record PerfilDTO(@NotBlank String nombre, String telefono, String passwordActual, String passwordNueva){}
  private final PerfilService servicio; private final UsuarioRepository usuarios;
  public PerfilController(PerfilService s, UsuarioRepository u){servicio=s; usuarios=u;}
  @GetMapping public Usuario me(Authentication auth){ return usuarios.findByEmail(auth.getName()).orElseThrow(); }
  @PutMapping public Usuario actualizar(Authentication auth, @RequestBody PerfilDTO b){
    Long id=usuarios.findByEmail(auth.getName()).orElseThrow().getId();
    return servicio.actualizar(id,b.nombre(),b.telefono(),b.passwordActual(),b.passwordNueva());
  }
}
