package com.example.SuperDuperDrive.controller;

import com.example.SuperDuperDrive.model.Note;
import com.example.SuperDuperDrive.services.NoteService;
import com.example.SuperDuperDrive.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String createOrEditNote(Authentication auth, Model model, @ModelAttribute Note note, RedirectAttributes redirectAttributes) {
        Integer userid = userService.getCurrentLoggedInUserId(auth);

        note.setUserid(userid);
       // redirectAttributes.addFlashAttribute("activeTab", "notes");
        try {
            noteService.createOrEditNote(note);
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("hasGenericError", true);
            e.printStackTrace();
        }
        model.addAttribute("redirectTab", "nav-notes-tab");
        model.addAttribute("activeTab", "notes");
        return "result";
    }

}
