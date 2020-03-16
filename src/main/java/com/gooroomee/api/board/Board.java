package com.gooroomee.api.board;

import lombok.*;
import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of="board_id")
@Entity
@Table(name="t_board")
public class Board {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long board_id;

    @Column(name="board_type", nullable = false, length=50)
    @Setter
    private String boardType;
}
