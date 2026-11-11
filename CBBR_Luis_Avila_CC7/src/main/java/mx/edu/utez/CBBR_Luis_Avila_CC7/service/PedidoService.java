package mx.edu.utez.CBBR_Luis_Avila_CC7.service;

import mx.edu.utez.CBBR_Luis_Avila_CC7.controller.DTO.PedidoRequestDTO;
import mx.edu.utez.CBBR_Luis_Avila_CC7.controller.DTO.PedidoResponseDTO;
import mx.edu.utez.CBBR_Luis_Avila_CC7.model.EstadoPedido;
import mx.edu.utez.CBBR_Luis_Avila_CC7.model.Pedido;
import mx.edu.utez.CBBR_Luis_Avila_CC7.model.Producto;
import mx.edu.utez.CBBR_Luis_Avila_CC7.repository.PedidoRepository;
import mx.edu.utez.CBBR_Luis_Avila_CC7.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        if (!producto.isActivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto no está activo");
        }

        if (producto.getStock() < dto.getCantidad()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }

        Pedido pedido = new Pedido();
        pedido.setNombreCliente(dto.getNombreCliente());
        pedido.setCantidad(dto.getCantidad());
        pedido.setPrecioUnitario(producto.getPrecio());
        pedido.setTotal(producto.getPrecio() * dto.getCantidad());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setProducto(producto);

        producto.setStock(producto.getStock() - dto.getCantidad());

        productoRepository.save(producto);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        return new PedidoResponseDTO(pedidoGuardado);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se pueden cancelar pedidos pendientes");
        }

        pedido.setEstado(EstadoPedido.CANCELADO);
        Producto producto = pedido.getProducto();
        producto.setStock(producto.getStock() + pedido.getCantidad());

        productoRepository.save(producto);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void procesarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se pueden procesar pedidos pendientes");
        }

        pedido.setEstado(EstadoPedido.PROCESADO);
        pedidoRepository.save(pedido);
    }

    public List<PedidoResponseDTO> consultarPorEstado(String estadoStr) {
        try {
            EstadoPedido estado = EstadoPedido.valueOf(estadoStr.toUpperCase());
            return pedidoRepository.findByEstado(estado).stream()
                    .map(PedidoResponseDTO::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido");
        }
    }

    public PedidoResponseDTO consultarDetalle(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        return new PedidoResponseDTO(pedido);
    }
}
