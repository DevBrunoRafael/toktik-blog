package com.ifs.edu.br.toktikblog.structure.graph;

// uso de record para criação das arestas
// record: tipo de classe que oferece a representação simples de um objeto dispensando a
// utilização de getters e setters e construtores, além de proporcionar que o objeto criado
// seja imutável
public record Edge<T>(Vertice<T> origin, Vertice<T> destiny) { }
