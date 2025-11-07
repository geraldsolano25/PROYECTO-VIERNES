package legado_barberia;

import com.legado_barberia.domain.Rol;
import com.legado_barberia.domain.ServicioBarberia;
import com.legado_barberia.repository.RolRepository;
import com.legado_barberia.repository.ServicioBarberiaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TiendaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaApplication.class, args);
    }

    // 🚀 Bloque que agrega datos iniciales (semillas)
    @Bean
    CommandLineRunner seed(RolRepository rolRepo, ServicioBarberiaRepository servRepo) {
        return args -> {
            rolRepo.findByNombre("CLIENTE").orElseGet(() -> {
                Rol r = new Rol();
                r.setNombre("CLIENTE");
                return rolRepo.save(r);
            });

            if (servRepo.count() == 0) {
                servRepo.saveAll(List.of(
                        crear("Corte clásico", "Corte básico", 30, 5000),
                        crear("Afeitado", "Toalla caliente", 20, 4000),
                        crear("Corte + Barba", "Combo completo", 45, 8000)
                ));
            }
        };
    }

    private ServicioBarberia crear(String n, String d, int min, int precio) {
        var s = new ServicioBarberia();
        s.setNombre(n);
        s.setDescripcion(d);
        s.setDuracionMin(min);
        s.setPrecio(precio);
        return s;
    }
}
