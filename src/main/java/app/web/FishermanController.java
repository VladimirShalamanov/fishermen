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
public class FishermanController {

    private final FishermanService fishermanService;
    private final FishService fishService;

    @Autowired
    public FishermanController(FishermanService fishermanService,
                               FishService fishService) {

        this.fishermanService = fishermanService;
        this.fishService = fishService;
    }

    @GetMapping("/edit-profile")
    public ModelAndView getProfilePage(HttpSession session) {

        ModelAndView model = new ModelAndView("edit-profile");

        UUID userId = (UUID) session.getAttribute("user_id");
        Fisherman fisherman = fishermanService.getById(userId);

        EditProfileRequest editProfileRequest = DtoMapper.fromUser(fisherman);

        model.addObject("editProfileRequest", editProfileRequest);
        model.addObject("fisherman", fisherman);

        return model;
    }

    @PutMapping("/edit-profile/{id}")
    public ModelAndView updateProfile(@Valid EditProfileRequest editProfileRequest,
                                      BindingResult bindingResult,
                                      @PathVariable UUID id) {

        if (bindingResult.hasErrors()) {
            Fisherman fisherman = fishermanService.getById(id);

            ModelAndView model = new ModelAndView("edit-profile");
            model.addObject("fisherman", fisherman);

            return model;
        }

        fishermanService.updateProfile(id, editProfileRequest);

        return new ModelAndView("redirect:/home");
    }
}
