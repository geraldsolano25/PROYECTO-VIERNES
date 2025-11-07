package com.legado_barberia.service;
import com.legado_barberia.domain.Usuario; import com.legado_barberia.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service;

@Service
public class PerfilService {
  private final UsuarioRepository repo; private final PasswordEncoder encoder;
  public PerfilService(UsuarioRepository r, PasswordEncoder e){repo=r; encoder=e;}
  public Usuario actualizar(Long id, String nombre, String tel, String passActual, String passNueva){
    Usuario u=repo.findById(id).orElseThrow(); u.setNombre(nombre); u.setTelefono(tel);
    if(passNueva!=null && !passNueva.isBlank()){
      if(passActual==null || !encoder.matches(passActual,u.getPasswordHash())) throw new IllegalArgumentException("Password actual inválido");
      u.setPasswordHash(encoder.encode(passNueva));
    }
    return repo.save(u);
  }
}
