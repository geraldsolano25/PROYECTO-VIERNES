package com.legado_barberia.service;
import com.legado_barberia.domain.*; import com.legado_barberia.repository.*; import com.legado_barberia.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AuthService {
  private final UsuarioRepository usuarios; private final RolRepository roles;
  private final PasswordEncoder encoder; private final JwtTokenProvider jwt;
  public AuthService(UsuarioRepository u, RolRepository r, PasswordEncoder e, JwtTokenProvider j){usuarios=u; roles=r; encoder=e; jwt=j;}
  public void register(String email,String password,String nombre,String telefono){
    if(usuarios.existsByEmail(email)) throw new IllegalArgumentException("Correo ya registrado");
    Rol rolCliente = roles.findByNombre("CLIENTE").orElseGet(()->{Rol r=new Rol(); r.setNombre("CLIENTE"); return roles.save(r);});
    Usuario u=new Usuario(); u.setEmail(email); u.setPasswordHash(encoder.encode(password)); u.setNombre(nombre); u.setTelefono(telefono); u.setRoles(Set.of(rolCliente));
    usuarios.save(u);
  }
  public String login(String email,String password){
    Usuario u=usuarios.findByEmail(email).orElseThrow(()->new IllegalArgumentException("Credenciales incorrectas"));
    if(!u.isActivo() || !encoder.matches(password,u.getPasswordHash())) throw new IllegalArgumentException("Credenciales incorrectas");
    return jwt.generate(u.getEmail(), Map.of("uid",u.getId(),"roles","CLIENTE"));
  }
}
