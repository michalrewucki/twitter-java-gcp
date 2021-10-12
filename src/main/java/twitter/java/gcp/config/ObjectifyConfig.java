package twitter.java.gcp.config;

import twitter.java.gcp.model.Tweet;
import twitter.java.gcp.model.User;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyFilter;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.spymemcached.SpyMemcacheService;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@Slf4j
public class ObjectifyConfig {

    @EventListener(ApplicationStartedEvent.class)
    public void initObjectify() throws IOException {
        ObjectifyService.init(new ObjectifyFactory(createSpyMemcacheService()));
        ObjectifyService.register(Tweet.class);
        ObjectifyService.register(User.class);
        log.info("Objectify started");
    }

    @Bean
    public FilterRegistrationBean<ObjectifyFilter> loggingFilter(){
        FilterRegistrationBean<ObjectifyFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ObjectifyFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    private SpyMemcacheService createSpyMemcacheService() throws IOException {
        MemcachedClient memcachedClient = new MemcachedClient(InetSocketAddress.createUnresolved("localhost", 11211));
        return new SpyMemcacheService(memcachedClient);
    }
}
