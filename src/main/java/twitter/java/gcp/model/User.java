package twitter.java.gcp.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnSave;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.beans.Transient;
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
public class User {

    @Id
    Long id;
    String name;
    Instant lastModifiedDateTime;

    @Transient
    public Key<User> getKey() {
        return buildKey(id);
    }

    @Transient
    public Ref<User> getRef() {
        return Ref.create(getKey());
    }

    @OnSave
    public void updateLastModifiedDateTime() {
        lastModifiedDateTime = Instant.now();
    }

    public static Key<User> buildKey(Long id) {
        return Key.create(User.class, id);
    }
}
