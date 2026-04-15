package com.example.iam_bucket_demo.controller;

import com.example.iam_bucket_demo.service.S3Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
    private final S3Service s3Service;
    public UploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("imageUrls", s3Service.listImageUrls());

        return "upload";
    }

    @PostMapping("/upload")
    public String upload(Model model, @RequestParam("file") MultipartFile file) {
        try {
            s3Service.uploadFile(file);
            model.addAttribute("success", file.getOriginalFilename());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("imageUrls", s3Service.listImageUrls());
        return "upload";
    }
}
