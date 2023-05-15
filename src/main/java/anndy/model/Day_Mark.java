package anndy.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "\"Day_mark\"")
public class Day_Mark {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mark", nullable = false)
    private Long mark;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date")
    private Date date;
}