package app.fish.service;

import app.fish.model.Fish;
import app.fish.property.FishProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FishInit implements ApplicationRunner {

    private final FishService fishService;
    private final FishProperties fishProperties;

    @Autowired
    public FishInit(FishService fishService, FishProperties fishProperties) {
        this.fishService = fishService;
        this.fishProperties = fishProperties;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Fish> fishes = fishService.getAllFishes();

        if (fishes.isEmpty()) {

            fishProperties.getFishes().forEach(fishService::createInit);
        }
    }
}