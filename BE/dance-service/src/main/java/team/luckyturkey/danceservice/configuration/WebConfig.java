package team.luckyturkey.danceservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.luckyturkey.danceservice.domain.FeedType;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, FeedType>() {
            @Override
            public FeedType convert(String source) {
                return FeedType.fromValue(Integer.parseInt(source));
            }
        });
    }
}
