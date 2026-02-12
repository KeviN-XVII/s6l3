package kevinquarta.s6l3.payloads;

import jakarta.validation.constraints.NotBlank;

public record BlogDTO(
        @NotBlank(message="")
        String categoria,
        @NotBlank(message="")
        String title,
        @NotBlank(message="")
        String content,
        int tempoDiLettura,
        Long userId) {
}
