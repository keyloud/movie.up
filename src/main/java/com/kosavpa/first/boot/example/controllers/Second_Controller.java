package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.entity.post.PostEntity;
import com.kosavpa.first.boot.example.dao.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Optional;


@Controller
public class Second_Controller {
    private java.sql.Date setDate() {
        return new Date(new GregorianCalendar().getTimeInMillis());
    }

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model){
        Iterable<PostEntity> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        return "blog";
    }

    @GetMapping("/add")
    public String add(){
        return "add";
    }

    @PostMapping("/add")
    public String blog(@RequestParam String title,
                       @RequestParam MultipartFile uploadFile,
                       @RequestParam String anons,
                       @RequestParam String fullText,
                       Model model){
        if(!uploadFile.isEmpty() ||
                uploadFile.getContentType().equals("image/jpeg") ||
                    uploadFile.getContentType().equals("image/png")){
            try {
                PostEntity post = new PostEntity(null, title, setDate(), anons, fullText);
                long postId = postRepository.save(post).getId();

                byte[] uploadFileBytes = uploadFile.getBytes();
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(
                                new File("src/main/resources/static/image_anons/" + title + "_" + postId + ".jpg")));

                bos.write(uploadFileBytes);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            model.addAttribute("fileError", "File maybe empty or has an extension other than jpg");

            return "/add";
        }

        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String post(@PathVariable(value = "id")long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<PostEntity> postOptional = postRepository.findById(id);
        PostEntity post = postOptional.get();
        model.addAttribute("moreAboutThePost", post);

        return "more";
    }

    @GetMapping("/blog/{id}/redact")
    public String redact(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog/id";
        }

        Optional<PostEntity> postOptional = postRepository.findById(id);
        PostEntity post = postOptional.get();
        model.addAttribute("moreAboutThePost", post);

        return "redact";
    }

    @PostMapping("/blog/{id}/redact")
    public String update(@RequestParam String title, @RequestParam String anons, @RequestParam String fullText, @PathVariable(value = "id") long id, Model model){
        PostEntity post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(fullText);
        post.setPublicationDate(setDate());
        postRepository.save(post);

        return "/blog/{id}";
    }

    @PostMapping("/blog/{id}/delete")
    public String remove(@PathVariable(value = "id")long id){
        postRepository.deleteById(id);

        return "redirect:/blog";
    }
}
