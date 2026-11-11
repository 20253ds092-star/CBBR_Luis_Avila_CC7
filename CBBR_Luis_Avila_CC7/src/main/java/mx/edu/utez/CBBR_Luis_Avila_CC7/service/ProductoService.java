package mx.edu.utez.CBBR_Luis_Avila_CC7.service;

import mx.edu.utez.CBBR_Luis_Avila_CC7.controller.DTO.ProductoDTO;
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
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public void desactivarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        if (pedidoRepository.existsByProductoIdAndEstado(id, EstadoPedido.PENDIENTE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto tiene pedidos pendientes");
        }

        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Transactional
    public void cancelarPedidosPendientes(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        List<Pedido> pedidosPendientes = pedidoRepository.findByProductoIdAndEstado(id, EstadoPedido.PENDIENTE);

        for (Pedido pedido : pedidosPendientes) {
            pedido.setEstado(EstadoPedido.CANCELADO);
            producto.setStock(producto.getStock() + pedido.getCantidad());
        }

        pedidoRepository.saveAll(pedidosPendientes);
        productoRepository.save(producto);
    }

    public List<ProductoDTO> consultarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria).stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> listarActivos() {
        return productoRepository.findByActivoTrue().stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }
}
