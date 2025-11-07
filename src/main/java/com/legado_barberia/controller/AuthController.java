package com.legado_barberia.controller;
import com.legado_barberia.service.AuthService;
import jakarta.validation.constraints.*; import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated; import org.springframework.web.bind.annotation.*;

@Validated @RestController @RequestMapping("/auth")
public class AuthController {
  private record RegisterDTO(@Email @NotBlank String email, @NotBlank String password, @NotBlank String nombre, String telefono){}
  private record LoginDTO(@Email @NotBlank String email, @NotBlank String password){}
  private record TokenDTO(String token){}
  private final AuthService auth; public AuthController(AuthService a){this.auth=a;}

  @PostMapping("/register") public ResponseEntity<Void> register(@RequestBody RegisterDTO b){ auth.register(b.email(), b.password(), b.nombre(), b.telefono()); return ResponseEntity.status(201).build(); }
  @PostMapping("/login") public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO b){ return ResponseEntity.ok(new TokenDTO(auth.login(b.email(), b.password()))); }
}
