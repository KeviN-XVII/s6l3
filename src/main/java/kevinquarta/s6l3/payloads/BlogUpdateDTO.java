package kevinquarta.s6l3.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BlogUpdateDTO(
        @NotBlank(message="La categoria è obbligatoria")
        @Size(min=2, max=30)
        String categoria,
        @NotBlank(message="Il titolo è obbligatorio")
        @Size(min=2, max=30)
        String title,
        @NotBlank(message="il contenuto è obbligatorio")
        String content,
        int tempoDiLettura) {
}
