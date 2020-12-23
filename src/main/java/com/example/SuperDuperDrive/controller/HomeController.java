package com.example.SuperDuperDrive.controller;

import com.example.SuperDuperDrive.model.Note;
import com.example.SuperDuperDrive.model.User;
import com.example.SuperDuperDrive.services.FileService;
import com.example.SuperDuperDrive.services.NoteService;
import com.example.SuperDuperDrive.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;

    public HomeController(FileService fileService, UserService userService,NoteService noteService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;

    }

    @GetMapping
    public String home(Authentication authentication, Model model){
        String currentUsername = authentication.getName();
        User user = userService.getUser(currentUsername);

        model.addAttribute("files", fileService.getFileNames(user.getUserid()));
        model.addAttribute("notes", noteService.getAllNotes(user.getUserid()));
        model.addAttribute("note", new Note());
        model.addAttribute("activeTab", "files");
        return "home";
    }
}
