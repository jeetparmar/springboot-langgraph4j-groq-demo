package com.example.groqgraph;

import org.bsc.langgraph4j.state.AgentState;
import org.bsc.langgraph4j.state.Channel;
import org.bsc.langgraph4j.state.Channels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Shared state passed between graph nodes. */
public class ChatState extends AgentState {

    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String FEEDBACK = "feedback";
    public static final String REVISIONS = "revisions";
    public static final String TRACE = "trace";

    // "trace" accumulates (appender); every other key is last-write-wins
    public static final Map<String, Channel<?>> SCHEMA =
            Map.of(TRACE, Channels.appender(ArrayList::new));

    public ChatState(Map<String, Object> init) {
        super(init);
    }

    public String question()  { return this.<String>value(QUESTION).orElse(""); }
    public String answer()    { return this.<String>value(ANSWER).orElse(""); }
    public String feedback()  { return this.<String>value(FEEDBACK).orElse(""); }
    public int revisions()    { return this.<Integer>value(REVISIONS).orElse(0); }
    public List<String> trace() { return this.<List<String>>value(TRACE).orElse(List.of()); }
}
