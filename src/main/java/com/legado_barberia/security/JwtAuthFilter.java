package com.legado_barberia.security;
import jakarta.servlet.*; import jakarta.servlet.http.*; import java.io.IOException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component; import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtAuthFilter extends GenericFilterBean {
  private final JwtTokenProvider jwt; private final CustomUserDetailsService uds;
  public JwtAuthFilter(JwtTokenProvider jwt, CustomUserDetailsService uds){this.jwt=jwt; this.uds=uds;}
  @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req=(HttpServletRequest)request;
    String header=req.getHeader("Authorization");
    if(StringUtils.hasText(header)&&header.startsWith("Bearer ")){
      String token=header.substring(7);
      try{
        String email=jwt.getSubject(token);
        UserDetails user=uds.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken auth=
          new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }catch(Exception ignored){}
    }
    chain.doFilter(request,response);
  }
}
