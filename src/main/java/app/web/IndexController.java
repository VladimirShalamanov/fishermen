package app.web;

import app.fish.model.Fish;
import app.fish.service.FishService;
import app.fisherman.model.Fisherman;
import app.fisherman.service.FishermanService;
import app.web.dto.LoginRequest;
import app.web.dto.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {
    // TODO: Implement the necessary here.

    private final FishermanService fishermanService;
    private final FishService fishService;

    @Autowired
    public IndexController(FishermanService fishermanService,
                           FishService fishService) {

        this.fishermanService = fishermanService;
        this.fishService = fishService;
    }

    @GetMapping
    public String getIndexPage() {

        return "index";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {

        ModelAndView model = new ModelAndView("register");
        model.addObject("registerRequest", new RegisterRequest());

        return model;
    }

    @PostMapping("/register")
    public String register(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        fishermanService.register(registerRequest);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {

        ModelAndView model = new ModelAndView("login");
        model.addObject("loginRequest", new LoginRequest());

        return model;
    }

    @PostMapping("/login")
    public String login(LoginRequest loginRequest, HttpSession session) {

        Fisherman fisherman = fishermanService.login(loginRequest);
        session.setAttribute("user_id", fisherman.getId());

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(HttpSession session) {

        ModelAndView model = new ModelAndView("home");

        UUID userId = (UUID) session.getAttribute("user_id");
        Fisherman fisherman = fishermanService.getById(userId);
        List<Fish> myFishes = fisherman.getFishes();
        List<Fish> fishes = fishService.getAllFishes();

        model.addObject("fisherman", fisherman);
        model.addObject("myFishes", myFishes);
        model.addObject("fishes", fishes);

        return model;
    }
}
