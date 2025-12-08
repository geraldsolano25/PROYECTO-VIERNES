/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.repository;
import com.tienda_vm.domain.Producto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    public List<Producto> findByActivoTrue();
    //Consulta derivada que da los productos de un rango 
    //de precios
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(
        BigDecimal precioInf, BigDecimal precioSup
    );
    
    @Query(value="SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(
        BigDecimal precioInf, BigDecimal precioSup
    );
    
    @Query(nativeQuery=true,
            
            value="SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(
        BigDecimal precioInf, BigDecimal precioSup
    );
    
    @Query(nativeQuery = true, 
       value = "SELECT * FROM producto p WHERE p.descripcion LIKE %:descripcion% ORDER BY p.descripcion ASC")
    public List<Producto> consultaPorDescripcionSQL(String descripcion);
}
