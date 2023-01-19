package com.ifs.edu.br.toktikblog.controller;

import com.ifs.edu.br.toktikblog.context.PersistenceContext;
import com.ifs.edu.br.toktikblog.models.Publication;
import com.ifs.edu.br.toktikblog.models.User;
import com.ifs.edu.br.toktikblog.structure.InvertedFile;
import com.ifs.edu.br.toktikblog.structure.graph.Graph;
import com.ifs.edu.br.toktikblog.structure.graph.GraphException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/toktik")
public class FeedController {

    @Autowired
    private InvertedFile invertedFile;

    private Graph<User> graph = PersistenceContext.userGraph;

    @GetMapping("/feed")
    public String feed(HttpSession session, Model model) throws GraphException {

        User authUser = (User) session.getAttribute("authenticated_user");

        if (authUser != null) {
            var pubs = invertedFile.getAllPublications();
            Collections.reverse(pubs);

            List<Publication> myPubs = new ArrayList<>();
            invertedFile.getAllPublications().forEach(publication -> {
                   if (publication.getUser().equals(authUser))
                       myPubs.add(publication);
            });

            var friends = graph.getListOfAdjacentVertices(authUser);

            model.addAttribute("user", authUser);
            model.addAttribute("pubList", pubs);
            model.addAttribute("myPubs", myPubs);
            model.addAttribute("friends", friends);

            return "feed";
        } else {
            return "redirect:/toktik/login";
        }
    }

    @PostMapping("/new-pub")
    public String savePublication(@RequestParam("name") String name,
                                @RequestParam("text") String text,
                                HttpSession session,
                                Model model) throws IOException {

        User authUser = (User) session.getAttribute("authenticated_user");

        Publication publication = new Publication(name, text, authUser);
        invertedFile.savePub(publication);

        return "redirect:/toktik/feed";
    }

}
