package kaioenzo.contabancaria.common.interfaces;

public interface ServiceIF<T, DTO> {
    T criar(DTO dto);

    T buscar(String id);
}
