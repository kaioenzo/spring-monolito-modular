package kaioenzo.contabancaria.common.interfaces;

import org.springframework.http.ResponseEntity;

public interface ControllerIF<T, DTO> {
    ResponseEntity<T> criar(DTO dto);

    ResponseEntity<T> buscar(String id);
}

