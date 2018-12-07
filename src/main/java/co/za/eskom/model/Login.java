package co.za.eskom.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString  @EqualsAndHashCode
@Table(name="LOGIN")
public class Login implements Serializable {
    private @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    @Column(name="USERNAME")
    private @NonNull @NotEmpty String username;
    @Column(name="PASSWORD")
    private @NonNull @NotEmpty  String password;
}
