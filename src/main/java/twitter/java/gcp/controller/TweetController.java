package twitter.java.gcp.controller;

import twitter.java.gcp.controller.dto.TweetDto;
import twitter.java.gcp.model.Tweet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static twitter.java.gcp.controller.mapper.TweetMapper.INSTANCE;
import static com.googlecode.objectify.ObjectifyService.ofy;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @GetMapping
    public ResponseEntity<List<TweetDto>> findAll() {
        List<Tweet> tweets = ofy().load().type(Tweet.class).list();

        List<TweetDto> tweetDtos = INSTANCE.tweetsToTweetDtos(tweets);
        return ResponseEntity.ok(tweetDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetDto> getTweet(@PathVariable Long id) {
        Tweet tweet = ofy().load().type(Tweet.class).id(id).now();

        TweetDto tweetDto = INSTANCE.tweetToTweetDto(tweet);
        return ResponseEntity.ok(tweetDto);
    }
}
