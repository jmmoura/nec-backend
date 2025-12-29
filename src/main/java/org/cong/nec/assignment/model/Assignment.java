package org.cong.nec.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cong.nec.person.model.Person;
import org.cong.nec.territory.model.Territory;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Person assignedTo;

    @Column(name = "assigned_at")
    private LocalDate assignedAt;

    @Column(name = "completed_at")
    private LocalDate completedAt;

    @ManyToOne
    private Territory territory;

}
