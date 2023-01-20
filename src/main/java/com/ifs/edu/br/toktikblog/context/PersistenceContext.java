package com.ifs.edu.br.toktikblog.context;

import com.ifs.edu.br.toktikblog.models.Publication;
import com.ifs.edu.br.toktikblog.models.User;
import com.ifs.edu.br.toktikblog.structure.graph.Graph;
import java.util.HashMap;

public class PersistenceContext {

    public static Graph<User> userGraph = new Graph<>();

    // estrutura que simula o armazenamento em txt
    public static HashMap<String, Publication> publications = new HashMap<>();

}
