package com.example.SuperDuperDrive.controller;

import com.example.SuperDuperDrive.model.Credential;
import com.example.SuperDuperDrive.services.CredentialService;
import com.example.SuperDuperDrive.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String createOrEditCredential(Authentication auth, Model model, @ModelAttribute Credential credential) {
        Integer userid = userService.getCurrentLoggedInUserId(auth);
        credential.setUserid(userid);
        credentialService.createOrEditCredential(credential);
        model.addAttribute("success", true);
        model.addAttribute("activeTab", "nav-credentials-tab");
        return "result";
    }

    @GetMapping("/delete-credential/{credentialid}")
    public String deleteCredential(Authentication auth, @PathVariable Integer credentialid, Model model) {
        Integer userid = userService.getCurrentLoggedInUserId(auth);
        credentialService.deleteCredential(credentialid, userid);
        model.addAttribute("success", true);
        model.addAttribute("activeTab", "nav-credentials-tab");
        return "result";
    }
}
