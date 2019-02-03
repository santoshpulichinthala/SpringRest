package co.za.eskom.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
@Table(name = "BOOKINGS")
public class Bookings implements Serializable {

    private  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;

}
