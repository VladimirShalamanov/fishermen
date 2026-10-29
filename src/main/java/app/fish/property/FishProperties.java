package app.fish.property;

import app.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties
@PropertySource(value = "fishes.yaml", factory = YamlPropertySourceFactory.class)
public class FishProperties {

    // TODO: Load fish properties here.
    // Hint: Make an inner public class here that matches the structure of fishes.yaml file
    private List<FishProp> fishes;

    @Data
    public static class FishProp {

        private String species;
        private int weight;
        private String type;
        private String imageUrl;
        private int price;
    }
}
