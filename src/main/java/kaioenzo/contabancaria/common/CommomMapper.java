package kaioenzo.contabancaria.common;

import kaioenzo.contabancaria.common.exceptions.UUIDInvalidoException;

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
