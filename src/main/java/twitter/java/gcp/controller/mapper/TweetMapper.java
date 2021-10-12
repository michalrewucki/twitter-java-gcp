package twitter.java.gcp.controller.mapper;

import twitter.java.gcp.controller.dto.TweetDto;
import twitter.java.gcp.model.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TweetMapper {
    TweetMapper INSTANCE = Mappers.getMapper(TweetMapper.class);

    @Mapping(source = "author.value", target = "author")
    TweetDto tweetToTweetDto(Tweet tweet);

    List<TweetDto> tweetsToTweetDtos(List<Tweet> tweets);
}
