/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
@Data
@Entity
@Table(name ="producto")
public class Producto implements Serializable{
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    
    
    //private Integer idCategoria;
    
    @Column(unique = true,nullable = false,length = 50)
    @NotNull(message = "La descripción no puede ser vacía")
    @Size(max = 50,message = "La descripción no puede ser menor a 50")
    private String descripcion;
    
    @Column(columnDefinition = "TEXT")
    private String detalle;
    
    @Column(precision = 12, scale =2)
    @NotNull(message = "El precio debe estar definido")
    @DecimalMin(value="0.00",inclusive=true,message="El precio debe ser mayor o igual a 0.00")
    private BigDecimal precio;
    
    @NotNull(message="Las existencias deben ser mayor o igual a 0")
    @Min(value=0,message="Las existencias deben ser mayor o igual a 0")
    private Integer existencias;
    
    @Column(length= 1024)
    @Size(max = 1024)
    private String rutaImagen;
    private boolean activo;
    
    @ManyToOne
    @JoinColumn(name="id_categoria")
    private Categoria categoria;
}