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
@Entity(name = "territory")
public class Territory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private String number;

    @Column(name = "warning_message")
    private String warningMessage;

    @Column(name = "map_image_url")
    private String mapImageUrl;

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL)
    private List<Block> blocks;

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL)
    private List<Assignment> assignments;

}
