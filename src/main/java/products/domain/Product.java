package products.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "product")
public class Product {

    @Id
    private Long id;
    private String name;
    private String description;
    private Date creationDate;
    private Date updateDate;
    private Date lastPurchasedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private Category category;

}
