package kevinquarta.s6l3.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserDTO(
        @NotBlank(message="Il nome è un campo obbligatorio")
        @Size(min=3, max=30,message = "Il nome proprio deve essere tra i 3 e i 30 caratteri")
        String name,
        @NotBlank(message="Il cognome è un campo obbligatorio")
        @Size(min=3, max=30,message = "Il cognome proprio deve essere tra i 3 e i 30 caratteri")
        String surname,
        @NotBlank(message="L'email è obbligatoria")
        @Email(message = "L'indirizzo email inserito non è nel formato corretto!")
        String email,
        @NotNull(message = "La data di nascita è obbligatoria")
        LocalDate dateOfBirth) {
}
