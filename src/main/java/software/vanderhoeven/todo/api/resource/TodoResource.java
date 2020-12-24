package software.vanderhoeven.todo.api.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import software.vanderhoeven.todo.data.model.Todo;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api")
public class TodoResource {

    private List<Todo> todos = Stream.of(
            Todo.builder().id(1L).title("Todo 1").completed(false).build(),
            Todo.builder().id(2L).title("Todo 2").completed(true).build(),
            Todo.builder().id(3L).title("Todo 3").completed(false).build()
    ).collect(Collectors.toList());

    @CrossOrigin
    @GetMapping(value = "/todo")
    public ResponseEntity<List<Todo>> getTodos(@QueryParam("limit") Integer limit) {
        if (limit != null) {
            return ResponseEntity.ok(todos.subList(0, Math.min(todos.size(), limit)));
        }
        return ResponseEntity.ok(todos);
    }

    @CrossOrigin
    @PostMapping(value = "/todo")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody final Todo todo,
                                           UriComponentsBuilder ucb) {
        todo.setId(todos.stream().mapToLong(x -> x.getId()).max().orElse(1L) + 1);
        todos.add(todo);
        URI uri = ucb.path("/api/todo")
                .path(String.valueOf(todo.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(uri).body(todo);
    }

    @CrossOrigin
    @DeleteMapping(value = "/todo/{id}")
    public ResponseEntity.HeadersBuilder<?> deleteTodo(@PathVariable final Long id) {
        todos = todos.stream().filter(x -> x.getId() != id).collect(Collectors.toList());
        return ResponseEntity.noContent();
    }


}
