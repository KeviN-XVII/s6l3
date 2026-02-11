package kevinquarta.s6l3.payloads;

import kevinquarta.s6l3.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class BlogPayload {
    private String categoria;
    private String title;
    private String content;
    private int tempoDiLettura;
    private Long userId;
}
