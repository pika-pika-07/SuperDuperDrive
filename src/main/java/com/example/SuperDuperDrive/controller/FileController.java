package com.example.SuperDuperDrive.controller;

import com.example.SuperDuperDrive.model.File;
import com.example.SuperDuperDrive.services.FileService;
import com.example.SuperDuperDrive.services.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
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
    public String uploadFile(Authentication auth, @RequestParam("fileUpload") MultipartFile file, Model model, RedirectAttributes redirectAttributes) throws IOException {
        Integer userid = userService.getCurrentLoggedInUserId(auth);
       // redirectAttributes.addFlashAttribute("activeTab", "files");
        if (fileService.isExistingFile(file, userid)) {
            model.addAttribute("hasErrorMsg", true);
            model.addAttribute("errorMsg", "File with same name already exists. File not uploaded.");
        } else {
            if(file.getSize()>2000000)
            {
                model.addAttribute("hasErrorMsg", true);
                model.addAttribute("errorMsg","Too large File to upload, Please upload a smaller file");
            }
            else{
                fileService.storeFile(file, userid);
                model.addAttribute("success", true);
            }


        }
        model.addAttribute("activeTab", "");
        return "result";
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer fileId, Authentication auth) {
        Integer userid = userService.getCurrentLoggedInUserId(auth);

        File file = fileService.getFile(fileId, userid);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(file.getFiledata()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + file.getFilename())
                .body(resource);
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(Authentication auth, @PathVariable Integer fileId, Model model, RedirectAttributes redirectAttributes) {
        Integer userid = userService.getCurrentLoggedInUserId(auth);
       // redirectAttributes.addFlashAttribute("activeTab", "files");
        fileService.deleteFile(fileId, userid);
        model.addAttribute("success", true);
        model.addAttribute("activeTab","");
        return "result";
    }
}
