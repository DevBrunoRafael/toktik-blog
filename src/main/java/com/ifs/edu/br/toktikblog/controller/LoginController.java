package com.ifs.edu.br.toktikblog.controller;

import com.ifs.edu.br.toktikblog.context.PersistenceContext;
import com.ifs.edu.br.toktikblog.models.User;
import com.ifs.edu.br.toktikblog.structure.graph.Graph;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/toktik")
public class LoginController {

    private Graph<User> graph = PersistenceContext.userGraph;

    // retorna página de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // loga usuário utilizando a criação de sessão
    @PostMapping("/login")
    public String logar(@RequestParam("email") String email,
                      @RequestParam("senha") String senha,
                      HttpSession session, Model model) {

        User authUser = null;

        for (var vertice : graph.allVertices()){
            var userRegistered = vertice.getData();
            if (userRegistered.getEmail().equals(email)) {
                authUser = userRegistered;
                break;
            }
        }

        if (authUser != null) {
            if (authUser.getSenha().equals(senha)) {
                session.setAttribute("authenticated_user", authUser);
                return "redirect:/toktik/feed";
            } else {
                model.addAttribute("login_error", "Senha incorreta.");
                return "login";
            }
        } else {
            model.addAttribute("login_error", "Email não cadastrado.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/toktik/login";
    }

}
