package zaf.oauth2.resource.server.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.List.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class GameController {

    public static final List<Game> GAMES = new ArrayList<>(of(
            new Game("1", "God of War", "Santa Monica Studio", LocalDate.parse("2018-04-20"))
    ));

    @GetMapping("games")
    @PreAuthorize("hasAuthority('SCOPE_read:games')")
    public ResponseEntity<List<Game>> getGames() {
        return ok(GAMES);
    }

    @GetMapping("games/game/{id}")
    @PreAuthorize("hasAuthority('SCOPE_read:games')")
    public ResponseEntity<Game> getGame(@PathVariable("id") String id) {
        return GAMES.stream()
                .filter(game -> game.id().equals(id))
                .map(ResponseEntity::ok)
                .findFirst()
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("games/game/add")
    @PreAuthorize("hasAuthority('SCOPE_edit:games')")
    public ResponseEntity<Game> getGame(@RequestBody Game game) {
        GAMES.add(game);
        return ResponseEntity.status(CREATED).build();
    }

    public record Game(String id, String name, String studio, LocalDate releaseDate) {
    }

}
