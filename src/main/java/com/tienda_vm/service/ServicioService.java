/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.service;
import com.tienda_vm.domain.Servicio;
import com.tienda_vm.repository.ServicioRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional(readOnly = true)
    public List<Servicio> getServicios(boolean activo) {
        if (activo) {
            return servicioRepository.findByActivoTrue();
        }
        return servicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Servicio> getServicio(Integer idServicio) {
        return servicioRepository.findById(idServicio);
    }

    @Transactional
    public void delete(Integer idServicio) {
        if (!servicioRepository.existsById(idServicio)) {
            throw new IllegalArgumentException("No existe un servicio con el id " + idServicio);
        }
        
        try {
            servicioRepository.deleteById(idServicio);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el servicio " + idServicio + 
                                           " porque tiene datos asociados: " + e.getMessage());
        }
    }

    @Transactional
    public void save(Servicio servicio, MultipartFile imagenFile) {
        servicio = servicioRepository.save(servicio);
        
        if (!imagenFile.isEmpty()) {
            try {
                String rutaImagen = firebaseStorageService.uploadImage(
                    imagenFile, 
                    "servicio", 
                    servicio.getIdServicio()
                );
                servicio.setRutaImagen(rutaImagen);
                servicioRepository.save(servicio);
                
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen: " + e.getMessage(), e);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Servicio> consultaPorPrecio(BigDecimal precioInf, BigDecimal precioSup) {
        return servicioRepository.findByPrecioBetweenOrderByPrecioAsc(precioInf, precioSup);
    }

    @Transactional(readOnly = true)
    public List<Servicio> consultaPorDuracion(Integer duracionMin, Integer duracionMax) {
        return servicioRepository.findByDuracionMinutosBetweenOrderByDuracionMinutosAsc(
            duracionMin, duracionMax
        );
    }

  

    @Transactional(readOnly = true)
    public List<Servicio> consultaSQL(BigDecimal precioInf, BigDecimal precioSup) {
        return servicioRepository.consultaSQL(precioInf, precioSup);
    }

    @Transactional(readOnly = true)
    public List<Servicio> consultaPorDescripcion(String descripcion) {
        return servicioRepository.consultaPorDescripcionSQL(descripcion);
    }
@Transactional(readOnly = true)
public List<Servicio> consultaDerivada(BigDecimal precioInf, BigDecimal precioSup) {
    // Este método usa la consulta derivada del repositorio
    return servicioRepository.findByPrecioBetweenOrderByPrecioAsc(precioInf, precioSup);
}
}