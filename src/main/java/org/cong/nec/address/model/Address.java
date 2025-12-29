package org.cong.nec.address.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cong.nec.block.model.Block;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String street;

    private String number;

    private LocalDateTime visitedAt;

    private LocalDateTime visitUnallowedAt;

    @ManyToOne
    private Block block;

}
