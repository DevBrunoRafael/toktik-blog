package com.ifs.edu.br.toktikblog.controller;

import com.ifs.edu.br.toktikblog.context.PersistenceContext;
import com.ifs.edu.br.toktikblog.models.User;
import com.ifs.edu.br.toktikblog.structure.graph.Graph;
import com.ifs.edu.br.toktikblog.structure.graph.GraphException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/toktik")
public class RegisterController {

    private Graph<User> graph = PersistenceContext.userGraph;

    // retorna página de cadastro
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // faz o cadastro de usuários
    @PostMapping("/register")
    public String register(@RequestParam("nome") String nome,
                         @RequestParam("email") String email,
                         @RequestParam("senha") String senha) throws GraphException {

        User user = new User(nome, email, senha);
        System.out.println(user);
        graph.addVertice(user);

        return "register";
    }

}
