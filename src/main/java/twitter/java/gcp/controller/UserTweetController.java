package twitter.java.gcp.controller;

import twitter.java.gcp.controller.dto.CreateUpdateTweetDto;
import twitter.java.gcp.controller.dto.TweetDto;
import twitter.java.gcp.model.Tweet;
import twitter.java.gcp.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static twitter.java.gcp.controller.mapper.TweetMapper.INSTANCE;
import static com.googlecode.objectify.ObjectifyService.ofy;

@RestController
@RequestMapping("/users/{userId}/tweets")
public class UserTweetController {

    @PutMapping("/{id}")
    public ResponseEntity<TweetDto> updateTweet(@PathVariable Long userId, @PathVariable Long id, @RequestBody  CreateUpdateTweetDto createUpdateTweetDto) {
        Tweet tweet = ofy().load()
                .type(Tweet.class)
                .id(id)
                .now();

        tweet.setContent(createUpdateTweetDto.getContent());

        ofy().save().entity(tweet).now();

        TweetDto tweetDto = INSTANCE.tweetToTweetDto(tweet);
        return ResponseEntity.ok(tweetDto);
    }

    @GetMapping
    public ResponseEntity<List<TweetDto>> findUserTweets(@PathVariable Long userId) {
        User user = ofy().load().type(User.class).id(userId).now();
        List<Tweet> tweets = ofy()
                .load()
                .type(Tweet.class)
                .filter("author", user.getRef())
                .list();

        List<TweetDto> tweetDtos = INSTANCE.tweetsToTweetDtos(tweets);
        return ResponseEntity.ok(tweetDtos);
    }

    @PostMapping
    public ResponseEntity<TweetDto> createTweet(@PathVariable Long userId,
                                                @RequestBody CreateUpdateTweetDto createUpdateTweetDto) {
        User user = ofy().load().type(User.class).id(userId).now();
        Tweet tweet = Tweet.builder()
                .content(createUpdateTweetDto.getContent())
                .author(user.getRef())
                .build();
        ofy().save().entity(tweet).now();

        TweetDto tweetDto = INSTANCE.tweetToTweetDto(tweet);
        return ResponseEntity.ok(tweetDto);
    }
}
