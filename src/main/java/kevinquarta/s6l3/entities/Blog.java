package kevinquarta.s6l3.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Random;
@Entity
@Table(name="blogs")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String categoria;
    private String title;
    private String cover;
    private String content;
    private int tempoDiLettura;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    public Blog(String categoria, String title,String content, int tempoDiLettura,User user) {
        this.categoria = categoria;
        this.title = title;
        this.cover = "https://picsum.photos/200/300";
        this.content = content;
        this.tempoDiLettura = tempoDiLettura;
        this.user = user;
        Random rndm = new Random();
        this.id = rndm.nextInt(1, 1000);
    }
}
