package com.kosavpa.first.boot.example.controllers;

import com.kosavpa.first.boot.example.model.entity.post.PostEntity;
import com.kosavpa.first.boot.example.model.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class Second_Controller {
    private String setDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy-k:m");
        return sdf.format(new Date());
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
    public String blog(@RequestParam String title, @RequestParam String anons, @RequestParam String fullText){
        PostEntity post = new PostEntity(title, setDate(), anons, fullText);
        postRepository.save(post);

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
