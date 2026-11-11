package mx.edu.utez.CBBR_Luis_Avila_CC7.repository;

import mx.edu.utez.CBBR_Luis_Avila_CC7.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByActivoTrue();
}
