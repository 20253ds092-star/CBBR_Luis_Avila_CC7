package mx.edu.utez.CBBR_Luis_Avila_CC7.controller.DTO;

import lombok.Data;
import mx.edu.utez.CBBR_Luis_Avila_CC7.model.Producto;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;
    private boolean activo;

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.categoria = producto.getCategoria();
        this.precio = producto.getPrecio();
        this.stock = producto.getStock();
        this.activo = producto.isActivo();
    }
}
