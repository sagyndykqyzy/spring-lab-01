
package kz.iitu.springlab.web;

import kz.iitu.springlab.security.AppUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    private final AppUserRepository repository;

    public AdminPageController(AppUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", repository.findAll());
        return "admin";
    }
}

