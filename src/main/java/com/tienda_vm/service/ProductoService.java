/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.service;

import com.tienda_vm.domain.Producto;
import com.tienda_vm.repository.ProductoRepository;
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
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //Este metodo recibe un id producto, se busca en la tabla y lo retorna,
    //se usa en el proceso de modificar
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {
        if (activo) {
            return productoRepository.findByActivoTrue();
        }
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer idProducto) {
        return productoRepository.findById(idProducto);
    }

    @Transactional
    public void delete(Integer idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new IllegalArgumentException("No existe una categoría con el id" + idProducto);

        }
        try {
            productoRepository.deleteById(idProducto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("No se puede eliminar la categoría " + idProducto + " porque tiene datos asociados:" + e);
        }
    }
    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional
    public void save(Producto producto, MultipartFile imagenFile) {
        producto = productoRepository.save(producto);
        if (!imagenFile.isEmpty()) //Si nos pasaron una imagen... se guarda en la nube
        {
            try {
                String rutaImagen = firebaseStorageService.uploadImage(imagenFile, "producto", producto.getIdProducto());
                producto.setRutaImagen(rutaImagen);
                productoRepository.save(producto);

            } catch (IOException e) {
                // Deberías manejar el error adecuadamente
                e.printStackTrace();
                // O lanzar una excepción personalizada
                throw new RuntimeException("Error al subir la imagen: " + e.getMessage(), e);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Producto> consultaDerivada(BigDecimal precioInf, BigDecimal precioSup) {

        return productoRepository.findByPrecioBetweenOrderByPrecioAsc(precioInf, precioSup);
    }

    @Transactional(readOnly = true)
    public List<Producto> consultaJPQL(BigDecimal precioInf, BigDecimal precioSup) {

        return productoRepository.consultaJPQL(precioInf, precioSup);
    }

    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(BigDecimal precioInf, BigDecimal precioSup) {

        return productoRepository.consultaSQL(precioInf, precioSup);
    }
    
   
    @Transactional(readOnly = true)
    public List<Producto> consultaPorDescripcion(String descripcion) {
        return productoRepository.consultaPorDescripcionSQL(descripcion);
    }
}
