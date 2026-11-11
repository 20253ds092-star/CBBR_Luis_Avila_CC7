package mx.edu.utez.CBBR_Luis_Avila_CC7.controller.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoRequestDTO {

    @NotNull
    private Long productoId;

    @NotBlank
    private String nombreCliente;

    @NotNull
    @Min(1)
    private Integer cantidad;
}
