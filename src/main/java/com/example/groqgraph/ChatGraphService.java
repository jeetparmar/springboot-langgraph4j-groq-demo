package com.example.groqgraph;

import java.util.Map;

import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.StateGraph;
import static org.bsc.langgraph4j.StateGraph.END;
import static org.bsc.langgraph4j.StateGraph.START;
import static org.bsc.langgraph4j.action.AsyncEdgeAction.edge_async;
import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;
import org.springframework.stereotype.Service;

import dev.langchain4j.model.chat.ChatModel;

@Service
public class ChatGraphService {

        private final CompiledGraph<ChatState> graph;

        public ChatGraphService(ChatModel model, GroqProperties props) throws Exception {

                this.graph = new StateGraph<>(ChatState.SCHEMA, ChatState::new)
                                .addNode("draft", node_async(s -> {
                                        String a = model.chat("Answer clearly and concisely:\n" + s.question());
                                        return Map.of(ChatState.ANSWER, a, ChatState.TRACE, "draft");
                                }))
                                .addNode("review", node_async(s -> {
                                        String f = model.chat(
                                                        """
                                                                        You are a strict reviewer. Question: %s
                                                                        Answer: %s
                                                                        Reply with exactly 'APPROVED' if the answer is correct and complete,
                                                                        otherwise list the concrete problems."""
                                                                        .formatted(s.question(), s.answer()));
                                        return Map.of(ChatState.FEEDBACK, f, ChatState.TRACE, "review");
                                }))
                                .addNode("revise", node_async(s -> {
                                        String a = model.chat("""
                                                        Question: %s
                                                        Previous answer: %s
                                                        Reviewer feedback: %s
                                                        Write an improved answer.""".formatted(s.question(), s.answer(),
                                                        s.feedback()));
                                        return Map.of(ChatState.ANSWER, a,
                                                        ChatState.REVISIONS, s.revisions() + 1,
                                                        ChatState.TRACE, "revise");
                                }))
                                .addEdge(START, "draft")
                                .addEdge("draft", "review")
                                .addConditionalEdges("review",
                                                edge_async(s -> s.feedback().trim().startsWith("APPROVED")
                                                                || s.revisions() >= props.maxRevisions() ? "done"
                                                                                : "again"),
                                                Map.of("done", END, "again", "revise"))
                                .addEdge("revise", "review")
                                .compile();
        }

        public ChatState run(String question) {
                return graph.invoke(Map.of(ChatState.QUESTION, question))
                                .orElseThrow(() -> new IllegalStateException("Graph produced no state"));
        }
}
