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

    // cria um novo vertice
    public void addVertice(T data) throws GraphException {
        var contains = this.getVertice(data);
        if (contains != null) throw new GraphException("duplicated vertice");

        Vertice<T> vertice = new Vertice<>(data);
        vertices.add(vertice);
    }

    // adiciona aresta entre dois vertices
    public void addEdge(T origin, T destiny) throws GraphException {
        var verticeOrigin = this.getVertice(origin);
        var verticeDestiny = this.getVertice(destiny);

        if (verticeOrigin == null || verticeDestiny == null)
            throw new GraphException("one of the informed vertices does not exist");

        this.existEdge(verticeOrigin, verticeDestiny);


        Edge<T> edge = new Edge<>(verticeOrigin, verticeDestiny);
        verticeOrigin.addOutputEdge(edge);
        verticeDestiny.addInputEdge(edge);
        edges.add(edge);
    }

    public Vertice<T> getVertice(T data) {
        for (var vertice : this.vertices) {
            if (vertice.getData().equals(data))
                return vertice;
        }
        return null;
    }

    // verifica se já existe uma aresta entre os dois vértices para
    // evitar a criação de arestas duplicadas
    public void existEdge(Vertice<T> origin, Vertice<T> destiny) throws GraphException {
        for (var edge : edges) {
            if (edge.origin().equals(origin) && edge.destiny().equals(destiny)){
                throw new GraphException("existing edge between the two vertices");
            }
        }
    }

    // lista as arestas que estão apontadas para o vértice informado
    public List<T> getListOfInputVertices(T data) throws GraphException {
        var vertice = getVertice(data);
        if (vertice == null)
            throw new GraphException("vertice does not exist");

        var inputEdgesList = vertice.getInputEdges();

        List<Vertice<T>> inputVerticesList = new ArrayList<>();
        for (var edge : inputEdgesList) {
            var inputVertice = edge.origin();
            inputVerticesList.add(inputVertice);
        }

        return inputVerticesList.stream().map(Vertice::getData).toList();
    }

    // lista as arestas para as quais o vértice informado aponta
    public List<T> getListOfOutputVertices(T data) throws GraphException {
        var vertice = getVertice(data);
        if (vertice == null)
            throw new GraphException("vertice does not exist");

        var outputEdgesList = vertice.getOutputEdges();

        List<Vertice<T>> outputVerticesList = new LinkedList<>();
        for (var edge : outputEdgesList) {
            var adjacent = edge.destiny();
            outputVerticesList.add(adjacent);
        }

        return outputVerticesList.stream().map(Vertice::getData).toList();
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

            for (var edge : verticeOrigin.getOutputEdges()) {

                Vertice<T> verticeDestiny = edge.destiny();
                str.append(verticeDestiny.getData().toString()).append(", ");
            }

            str.append("\n");
        }

        return str.toString();
    }
}