/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.repository;

import com.tienda_vm.domain.Cita;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    
    // Métodos que JPA puede generar automáticamente
    // (los nombres deben coincidir exactamente con las propiedades de la entidad)
    
    // Buscar por estado
    List<Cita> findByEstado(String estado);
    
    // Buscar por estado y ordenar por fechaHora
    List<Cita> findByEstadoOrderByFechaHoraAsc(String estado);
    
    // Buscar entre fechas
    List<Cita> findByFechaHoraBetween(LocalDateTime start, LocalDateTime end);
    
    // Buscar por sucursal (usando sucursal.id)
    List<Cita> findBySucursalId(Integer sucursalId);
    
    // Buscar por nombre de cliente (contiene, ignorando mayúsculas/minúsculas)
    List<Cita> findByClienteContainingIgnoreCase(String cliente);
}