/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.service;

import com.tienda_vm.domain.Cita;
import com.tienda_vm.repository.CitaRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class CitaService {
    
    @Autowired
    private CitaRepository citaRepository;

    @Transactional(readOnly = true)
    public List<Cita> getCitas() {
        return citaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cita> getCitasPorEstado(String estado) {
        return citaRepository.findByEstado(estado);
    }
    
    @Transactional(readOnly = true)
    public List<Cita> getCitasPorFecha(LocalDate fecha) {
        LocalDateTime startOfDay = fecha.atStartOfDay();
        LocalDateTime endOfDay = fecha.atTime(LocalTime.MAX);
        return citaRepository.findByFechaHoraBetween(startOfDay, endOfDay);
    }
    
    @Transactional(readOnly = true)
    public List<Cita> getCitasPendientes() {
        return citaRepository.findByEstadoOrderByFechaHoraAsc("PENDIENTE");
    }
    
    @Transactional(readOnly = true)
    public List<Cita> getCitasHoy() {
        return getCitasPorFecha(LocalDate.now());
    }
    
    @Transactional(readOnly = true)
    public List<Cita> getCitasPorSucursal(Integer sucursalId) {
        return citaRepository.findBySucursalId(sucursalId);
    }
    
    @Transactional(readOnly = true)
    public List<Cita> getCitasPorCliente(String nombreCliente) {
        return citaRepository.findByClienteContainingIgnoreCase(nombreCliente);
    }

    @Transactional(readOnly = true)
    public Cita getCita(Integer idCita) {
        return citaRepository.findById(idCita).orElseThrow(
            () -> new NoSuchElementException("Cita con ID " + idCita + " no encontrada."));
    }

    @Transactional
    public void save(Cita cita) {
        citaRepository.save(cita);
    }

    @Transactional
    public void delete(Integer idCita) {
        if (!citaRepository.existsById(idCita)) {
            throw new IllegalArgumentException("La Cita con ID " + idCita + " no existe.");
        }
        try {
            citaRepository.deleteById(idCita);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar la cita. Tiene datos asociados.", e);
        }
    }
}