package org.cong.nec.territory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cong.nec.assignment.model.Assignment;
import org.cong.nec.block.model.Block;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Territory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String number;

    private String warningMessage;

    private String mapImageUrl;

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL)
    private List<Block> blocks;

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL)
    private List<Assignment> assignments;

}
