package org.cong.nec.block.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cong.nec.address.model.Address;
import org.cong.nec.territory.model.Territory;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @ManyToOne
    private Territory territory;

}
