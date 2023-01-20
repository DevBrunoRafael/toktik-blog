package com.ifs.edu.br.toktikblog.structure.graph;

import java.util.LinkedList;

public class Vertice<T> {

    // dado do vértice
    private final T data;

    // armazena as arestas que estão sendo apontadas para este vértice
    private final LinkedList<Edge<T>> inputEdges;

    // armazena lista de arestas para as quais este vértice aponta
    private final LinkedList<Edge<T>> outputEdges;

    public Vertice(T data) {
        this.data = data;
        this.inputEdges = new LinkedList<>();
        this.outputEdges = new LinkedList<>();
    }

    // abaixo estão presentes métodos de acesso aos dados acima
    public T getData() {
        return data;
    }

    public void addInputEdge(Edge<T> edge) {
        inputEdges.add(edge);
    }

    public void addOutputEdge(Edge<T> edge) {
        outputEdges.add(edge);
    }

    public LinkedList<Edge<T>> getInputEdges() {
        return inputEdges;
    }

    public LinkedList<Edge<T>> getOutputEdges() {
        return outputEdges;
    }
}