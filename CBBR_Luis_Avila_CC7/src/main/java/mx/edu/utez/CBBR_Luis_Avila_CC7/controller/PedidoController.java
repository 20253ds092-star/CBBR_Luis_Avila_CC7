package mx.edu.utez.CBBR_Luis_Avila_CC7.controller;

import jakarta.validation.Valid;
import mx.edu.utez.CBBR_Luis_Avila_CC7.controller.DTO.PedidoRequestDTO;
import mx.edu.utez.CBBR_Luis_Avila_CC7.controller.DTO.PedidoResponseDTO;
import mx.edu.utez.CBBR_Luis_Avila_CC7.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoRequestDTO requestDTO) {
        PedidoResponseDTO response = pedidoService.crearPedido(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/procesar")
    public ResponseEntity<Void> procesarPedido(@PathVariable Long id) {
        pedidoService.procesarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponseDTO>> consultarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pedidoService.consultarPorEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> consultarDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.consultarDetalle(id));
    }
}
