package kevinquarta.s6l3.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
public class UserPayload {
    @NotBlank(message="Il nome è un campo obbligatorio")
    @Size(min=3, max=30,message = "Il nome proprio deve essere tra i 3 e i 30 caratteri")
    private String name;
    @NotBlank(message="Il cognome è un campo obbligatorio")
    @Size(min=3, max=30,message = "Il cognome proprio deve essere tra i 3 e i 30 caratteri")
    private String surname;
    @NotBlank(message="L'email è obbligatoria")
    @Email(message = "L'indirizzo email inserito non è nel formato corretto!")
    private String email;
    @NotBlank(message="La data di nascita è un campo obbligatorio")
    private LocalDate dateOfBirth;

    public UserPayload(String name, String surname, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }
}
