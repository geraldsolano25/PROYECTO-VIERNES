/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.repository;
import com.tienda_vm.domain.Servicio;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    
    public List<Servicio> findByActivoTrue();
    
    // Consulta por rango de precios
    public List<Servicio> findByPrecioBetweenOrderByPrecioAsc(
        BigDecimal precioInf, 
        BigDecimal precioSup
    );

    // Consulta por duración
    public List<Servicio> findByDuracionMinutosBetweenOrderByDuracionMinutosAsc(
        Integer duracionMin, 
        Integer duracionMax
    );
    
 
    
    // Consulta SQL nativa
    @Query(nativeQuery = true,
           value = "SELECT * FROM servicio s WHERE s.precio BETWEEN :precioInf AND :precioSup ORDER BY s.precio ASC")
    public List<Servicio> consultaSQL(
        BigDecimal precioInf, 
        BigDecimal precioSup
    );
    
    // Consulta por descripción
    @Query(nativeQuery = true,
           value = "SELECT * FROM servicio s WHERE s.descripcion LIKE %:descripcion% ORDER BY s.descripcion ASC")
    public List<Servicio> consultaPorDescripcionSQL(String descripcion);
    
    
}