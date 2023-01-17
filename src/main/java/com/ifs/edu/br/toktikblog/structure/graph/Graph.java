package com.ifs.edu.br.toktikblog.structure.graph;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class Graph<T> {

    private final List<Vertice<T>> vertices;
    private final List<Edge<T>> edges;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addVertice(T data) throws GraphException {
        var contains = this.getVertice(data);
        if (contains != null) throw new GraphException("duplicated vertice");

        Vertice<T> vertice = new Vertice<>(data);
        vertices.add(vertice);
    }

    public void addEdge(T origin, T destiny) throws GraphException {
        var verticeOrigin = this.getVertice(origin);
        var verticeDestiny = this.getVertice(destiny);

        if (verticeOrigin == null || verticeDestiny == null)
            throw new GraphException("one of the informed vertices does not exist");

        Edge<T> edge = new Edge<>(verticeOrigin, verticeDestiny);
        verticeOrigin.addEdgeAdjacent(edge);
        edges.add(edge);
    }

    public Vertice<T> getVertice(T data) {
        for (var vertice : this.vertices) {
            if (vertice.getData().equals(data))
                return vertice;
        }
        return null;
    }

    public List<T> getListOfAdjacentVertices(T data) throws GraphException {
        var vertice = getVertice(data);
        if (vertice == null)
            throw new GraphException("vertice does not exist");

        var adjacencyList = vertice.getAdjacencyList();

        List<Vertice<T>> adjacentVertices = new LinkedList<>();
        for (var edge : adjacencyList) {
            var adjacent = edge.destiny();
            adjacentVertices.add(adjacent);
        }

        return adjacentVertices.stream().map(Vertice::getData).toList();
    }

    public int sizeVertices() {
        return this.vertices.size();
    }

    public int sizeEdges() {
        return this.edges.size();
    }

    public List<Vertice<T>> allVertices() {
        return this.vertices;
    }

    public List<Edge<T>> allEdges() {
        return this.edges;
    }


    public String toString() {

        StringBuilder str = new StringBuilder();

        for (var verticeOrigin : this.vertices) {
            str.append(verticeOrigin.getData().toString()).append(" -> ");

            for (var edge : verticeOrigin.getAdjacencyList()) {

                Vertice<T> verticeDestiny = edge.destiny();
                str.append(verticeDestiny.getData().toString()).append(", ");
            }

            str.append("\n");
        }

        return str.toString();
    }
}