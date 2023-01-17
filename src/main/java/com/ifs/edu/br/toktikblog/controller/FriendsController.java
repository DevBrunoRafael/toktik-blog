package com.ifs.edu.br.toktikblog.controller;

import com.ifs.edu.br.toktikblog.context.PersistenceContext;
import com.ifs.edu.br.toktikblog.models.User;
import com.ifs.edu.br.toktikblog.structure.graph.Graph;
import com.ifs.edu.br.toktikblog.structure.graph.GraphException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/toktik")
public class FriendsController {

    private Graph<User> graph = PersistenceContext.userGraph;

    @GetMapping("/users/{name}")
    public ResponseEntity<List<User>> findUsersByName(@PathVariable("name") String name) {
        List<User> opcoes = new ArrayList<>();

        graph.allVertices().forEach(vertice -> {
            User user = vertice.getData();
            if (user.getNome().contains(name)) opcoes.add(user);
        });

        return ResponseEntity.status(HttpStatus.OK).body(opcoes);
    }

    @PatchMapping("/users/add-friend/{friend-id}")
    public void addFriend(@PathVariable("friend-id") String friendId, HttpSession session) throws GraphException {

        User authUser = (User) session.getAttribute("authenticated_user");

        for (var vertice : graph.allVertices()) {
            User user = vertice.getData();
            if (user.getUuid().equals(friendId)) {
                graph.addEdge(authUser, user);
            }
        }
    }
}
