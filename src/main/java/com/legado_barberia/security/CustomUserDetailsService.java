package com.legado_barberia.security;
import com.legado_barberia.domain.Usuario; import com.legado_barberia.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*; import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service; import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UsuarioRepository repo;
  public CustomUserDetailsService(UsuarioRepository repo){this.repo=repo;}
  @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario u = repo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("No encontrado: "+email));
    return new User(u.getEmail(), u.getPasswordHash(), u.isActivo(), true,true,true,
      u.getRoles().stream().map(r->new SimpleGrantedAuthority("ROLE_"+r.getNombre())).collect(Collectors.toSet()));
  }
}
