package web.aurela.aurelaweb.Dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {
    private Long id;
    private String title;
    private String url;
    private boolean isPrincipal;
}

