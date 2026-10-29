package app.web;

import app.fish.model.Fish;
import app.fish.service.FishService;
import app.fisherman.model.Fisherman;
import app.fisherman.service.FishermanService;
import app.web.dto.DtoMapper;
import app.web.dto.EditProfileRequest;
import app.web.dto.LoginRequest;
import app.web.dto.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class FishingSpotController {

    private final FishermanService fishermanService;
    private final FishService fishService;

    @Autowired
    public FishingSpotController(FishermanService fishermanService,
                                 FishService fishService) {

        this.fishermanService = fishermanService;
        this.fishService = fishService;
    }

    @GetMapping("/fishing-spot")
    public ModelAndView getIndexPage() {

        ModelAndView model = new ModelAndView("fishing-spot");

        model.addObject("fish", fishService.getRandomFish());

        return model;
    }
}
