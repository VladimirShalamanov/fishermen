package app.fish.service;

import app.fish.model.Fish;
import app.fish.property.FishProperties.FishProp;
import app.fish.repository.FishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class FishService {

    private final FishRepository fishRepository;

    @Autowired
    public FishService(FishRepository fishRepository) {

        this.fishRepository = fishRepository;
    }

    public void createInit(FishProp fish) {

        Fish newFish = Fish.builder()
                .species(fish.getSpecies())
                .weight(fish.getWeight())
                .type(fish.getType())
                .imageUrl(fish.getImageUrl())
                .price(fish.getPrice())
                .build();

        fishRepository.save(newFish);
    }

    public List<Fish> getAllFishes() {

        return fishRepository.findAll();
    }

    public Fish getRandomFish() {

        List<Fish> fishes = fishRepository.findAll();

        if (fishes.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(fishes.size());

        return fishes.get(index);
    }
}
