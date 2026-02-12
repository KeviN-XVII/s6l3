package kevinquarta.s6l3.payloads;

import java.time.LocalDateTime;

public record ErrorsWithListDTO(String message, LocalDateTime timestamp) {
}
