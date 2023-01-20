package com.ifs.edu.br.toktikblog;

import com.ifs.edu.br.toktikblog.context.PersistenceContext;
import com.ifs.edu.br.toktikblog.models.Publication;
import com.ifs.edu.br.toktikblog.models.User;
import com.ifs.edu.br.toktikblog.service.MailService;
import com.ifs.edu.br.toktikblog.structure.InvertedFile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class ToktikBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToktikBlogApplication.class, args);
    }

    @Bean
    CommandLineRunner run(InvertedFile invertedFile){
        return args -> {
            User user1 = new User("Bruno Rafael", "brunorafaeldls2016@gmail.com", "admin1", "1001" );
            User user2 = new User("Bruno Acadêmico", "bruno.santos092@academico.ifs.edu.br", "admin2", "1002" );
            User user3 = new User("Conta hyper", "contahyperxfreefire@gmail.com", "admin3", "1003" );
            User user4 = new User("Marcus Vinícius", "marcusvini522@gmail.com", "admin4", "1004" );


            PersistenceContext.userGraph.addVertice(user1);
            PersistenceContext.userGraph.addVertice(user2);
            PersistenceContext.userGraph.addVertice(user3);
            PersistenceContext.userGraph.addVertice(user4);

            PersistenceContext.userGraph.addEdge(user1, user2);
            PersistenceContext.userGraph.addEdge(user1, user3);
            PersistenceContext.userGraph.addEdge(user1, user4);


            Publication publication1 = new Publication("1", "Pub 1", "batata frita quente", user3);
            Publication publication2 = new Publication("2", "Pub 2", "purê de batata", user3);
            Publication publication3 = new Publication("3", "Pub 3", "Carne frita com bacon", user3);
            Publication publication4 = new Publication("4", "Pub 1", "Cachorro caramelo", user2);
            Publication publication5 = new Publication("5", "Pub 2", "O cachorro da rua morde", user2);
            Publication publication6 = new Publication("6", "Pub 3", "O papagaio fala", user2);
            Publication publication7 = new Publication("7", "Backend java", "Java e spring, melhor combinação para backend", user1);
            Publication publication8 = new Publication("8", "Backend javascript", "NestJs e prisma, são ótimas opções para microserviços", user1);
            Publication publication9 = new Publication("9", "Java", "Spring boot facilita o desenvolvimento backend.", user1);
            Publication publication10 = new Publication("10", "Java", "Typescript é um javascript turbinado com tipagem", user1);

            invertedFile.savePub(publication1);
            invertedFile.savePub(publication2);
            invertedFile.savePub(publication3);
            invertedFile.savePub(publication4);
            invertedFile.savePub(publication5);
            invertedFile.savePub(publication6);
            invertedFile.savePub(publication7);
            invertedFile.savePub(publication8);
            invertedFile.savePub(publication9);
            invertedFile.savePub(publication10);

        };
    }

}
