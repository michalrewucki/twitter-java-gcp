package twitter.java.gcp.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Cache(expirationSeconds = 180)
public class Tweet {

    @Id
    Long id;
    String content;
    @Load
    @Index
    Ref<User> author;
    Instant lastModifiedDateTime;

    @OnSave
    public void updateLastModifiedDateTime() {
        lastModifiedDateTime = Instant.now();
    }
}
