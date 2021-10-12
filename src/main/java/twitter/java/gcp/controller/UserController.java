
package twitter.java.gcp.controller;

import twitter.java.gcp.controller.dto.CreateUpdateUserDto;
import twitter.java.gcp.model.Tweet;
import twitter.java.gcp.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUpdateUserDto createUpdateUserDto) {
        User user = User.builder()
                .name(createUpdateUserDto.getName())
                .build();
        ofy().save().entity(user).now();
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = ofy().load().type(User.class).list();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = ofy().load().type(User.class).id(id).now();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = ofy().load().type(User.class).id(id).now();

        ofy().transact(() -> {
            ofy().delete().entity(user).now();

            List<Tweet> tweets = ofy()
                    .load()
                    .type(Tweet.class)
                    .filter("author", user.getRef())
                    .list();

            ofy().delete().entities(tweets).now();
        });

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        User user = ofy().load().type(User.class).id(id).now();
        user.setName(createUpdateUserDto.getName());

        ofy().save().entity(user).now();
        return ResponseEntity.ok(user);
    }
}
