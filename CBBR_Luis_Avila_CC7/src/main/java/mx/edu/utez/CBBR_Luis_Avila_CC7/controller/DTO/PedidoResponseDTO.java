package mx.edu.utez.CBBR_Luis_Avila_CC7.controller.DTO;

import lombok.Data;
import mx.edu.utez.CBBR_Luis_Avila_CC7.model.EstadoPedido;
import mx.edu.utez.CBBR_Luis_Avila_CC7.model.Pedido;

@Data
public class PedidoResponseDTO {
    private Long id;
    private String nombreCliente;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    private EstadoPedido estado;
    private ProductoDTO producto;

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.nombreCliente = pedido.getNombreCliente();
        this.cantidad = pedido.getCantidad();
        this.precioUnitario = pedido.getPrecioUnitario();
        this.total = pedido.getTotal();
        this.estado = pedido.getEstado();
        this.producto = new ProductoDTO(pedido.getProducto());
    }
}
