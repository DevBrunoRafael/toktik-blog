package com.ifs.edu.br.toktikblog.structure.graph;

import java.util.LinkedList;

public class Vertice<T> {

    private final T data;
    private final LinkedList<Edge<T>> adjacencyList;

    public Vertice(T data) {
        this.data = data;
        this.adjacencyList = new LinkedList<>();
    }

    public void addEdgeAdjacent(Edge<T> edge) {
        adjacencyList.add(edge);
    }

    public T getData() {
        return data;
    }

    public LinkedList<Edge<T>> getAdjacencyList() {
        return adjacencyList;
    }
}