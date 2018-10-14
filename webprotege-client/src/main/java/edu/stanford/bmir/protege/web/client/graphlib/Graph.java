package edu.stanford.bmir.protege.web.client.graphlib;

import com.google.gwt.dom.client.Node;
import edu.stanford.bmir.protege.web.client.JSON;
import edu.stanford.bmir.protege.web.client.dagre.Dagre;
import jsinterop.annotations.*;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 13 Oct 2018
 */
@SuppressWarnings("Convert2MethodRef")
@JsType(isNative = true, namespace = "graphlib")
public class Graph {


    private Graph() {}

    @JsOverlay
    public static Graph create() {
        Graph g = new Graph();
        g.setGraphLabel(new GraphDetails());
        return g;
    }

    @JsOverlay
    public final String writeJson() {
        return JSON.stringify(GraphLibJson.write(this));
    }

    @JsOverlay
    public static Graph readJson(@Nonnull String s) {
        return GraphLibJson.read(JSON.parse(s));
    }

    /**
     * Lays out this graph
     */
    @JsOverlay
    public final void layout() {
        Dagre.get().layout(this);
    }

    @JsOverlay
    public final boolean isAcyclic() {
        return GraphLibAlgorithm.isAcyclic(this);
    }

    @JsMethod(name = "setGraph")
    public native void setGraphLabel(GraphDetails object);

    @JsMethod(name = "graph")
    public native GraphDetails getGraphLabel();


    @JsOverlay
    public final int getWidth() {
        return getGraphLabel().getWidth();
    }

    @JsOverlay
    public final int getHeight() {
        return getGraphLabel().getHeight();
    }

    @JsOverlay
    public final void addNode(@Nonnull NodeDetails node) {
        setNode(node.getId(), node);
    }

    @JsOverlay
    public final void addEdge(@Nonnull NodeDetails tailNode,
                              @Nonnull NodeDetails headNode,
                              @Nonnull EdgeDetails edge) {
        setEdge(tailNode.getId(), headNode.getId(), edge);
    }

    @JsOverlay
    public final Stream<NodeDetails> getNodes() {
        return Stream.of(nodes()).map(id -> node(id));
    }

    @JsOverlay
    public final Stream<EdgeDetails> getEdges() {
        return Stream.of(edges()).map(ek -> edge(ek));
    }

    public native EdgeDetails edge(EdgeKey e);

    @JsMethod(name = "nodeCount")
    public native int getNodeCount();

    @JsMethod(name = "edgeCount")
    public native int getEdgeCount();

    @JsMethod
    private native String [] sources();

    @JsOverlay
    public final Stream<NodeDetails> getSources() {
        return Stream.of(sources()).map(id -> node(id));
    }

    @JsMethod
    private native String [] sinks();

    @JsOverlay
    public final Stream<NodeDetails> getSinks() {
        return Stream.of(sinks()).map(id -> node(id));
    }

    @JsMethod
    private native String [] successors(String nodeId);

    @JsOverlay
    public final Stream<NodeDetails> getSuccessors(String nodeId) {
        return Stream.of(successors(nodeId)).map(id -> node(id));
    }

    @JsMethod
    private native String [] predecessors(String nodeId);

    @JsOverlay
    public final Stream<NodeDetails> getPredecessors(String nodeId) {
        return Stream.of(predecessors(nodeId)).map(id -> node(id));
    }

    private native EdgeKey [] edges();

    public native void setNode(@Nonnull String id, NodeDetails n);

    public native void setEdge(@Nonnull String tailNodeId,
                               @Nonnull String headNodeId,
                               @Nonnull EdgeDetails edge);

    private native String [] nodes();

    public native NodeDetails node(@Nonnull String id);
}
