package com.ifs.edu.br.toktikblog.controller;

import com.ifs.edu.br.toktikblog.models.Publication;
import com.ifs.edu.br.toktikblog.structure.InvertedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/toktik")
public class FindPublicationsController {

    @Autowired
    private InvertedFile invertedFile;

    // busca e retorna publicações de acordo com texto ou palavra informada
    @GetMapping("/find/{text-input}")
    public ResponseEntity<List<Publication>> findPublications(@PathVariable("text-input") String textInput) {
        var response = invertedFile.findByTextFragment(textInput);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
