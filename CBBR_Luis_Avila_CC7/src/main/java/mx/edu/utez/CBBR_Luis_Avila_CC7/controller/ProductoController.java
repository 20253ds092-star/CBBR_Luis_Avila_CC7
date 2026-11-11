package mx.edu.utez.CBBR_Luis_Avila_CC7.controller;

import mx.edu.utez.CBBR_Luis_Avila_CC7.controller.DTO.ProductoDTO;
import mx.edu.utez.CBBR_Luis_Avila_CC7.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarProducto(@PathVariable Long id) {
        productoService.desactivarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancelar-pedidos")
    public ResponseEntity<Void> cancelarPedidosPendientes(@PathVariable Long id) {
        productoService.cancelarPedidosPendientes(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> consultarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.consultarPorCategoria(categoria));
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ProductoDTO>> listarActivos() {
        return ResponseEntity.ok(productoService.listarActivos());
    }
}
