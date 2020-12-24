package software.vanderhoeven.todo.data.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    private Long id;

    private String title;

    private Boolean completed;

}
