package com.ifs.edu.br.toktikblog.structure.graph;

import java.util.LinkedList;

public class Vertice<T> {

    private final T data;
    private final LinkedList<Edge<T>> inputEdges;
    private final LinkedList<Edge<T>> outputEdges;

    public Vertice(T data) {
        this.data = data;
        this.inputEdges = new LinkedList<>();
        this.outputEdges = new LinkedList<>();
    }

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