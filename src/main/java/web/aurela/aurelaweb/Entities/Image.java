package web.aurela.aurelaweb.Entities;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;

    @Column(name = "is_principal")
    private boolean isPrincipal;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
