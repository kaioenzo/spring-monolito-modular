package kaioenzo.contabancaria.common;

import java.util.UUID;

public class CommomMapper {

    public static UUID converterUUID(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new UUIDInvalidoException();
        }
    }
}
