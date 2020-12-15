package com.example.SuperDuperDrive.controller;

import com.example.SuperDuperDrive.services.FileService;
import com.example.SuperDuperDrive.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService storageService, UserService userService) {
        this.fileService = storageService;
        this.userService = userService;
    }

    @PostMapping
    public String uploadFile(Authentication auth, @RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
        Integer userid = userService.getCurrentLoggedInUserId(auth);
        if (fileService.isExistingFile(file, userid)) {
            model.addAttribute("hasErrorMsg", true);
            model.addAttribute("errorMsg", "File with same name already exists. File not uploaded.");
        } else {
            fileService.storeFile(file, userid);
            model.addAttribute("success", true);
        }
        model.addAttribute("redirectTab", "");
        return "result";
    }
}
