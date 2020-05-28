package paper.util;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SentenceSplitter {
    private final Queue<String> sentenceBuffer = new ConcurrentLinkedQueue<String>();
    public SentenceSplitter(String srcArticle) {
        sentenceBuffer.addAll(Arrays.asList(srcArticle.split("\\. ")));
    }
    public boolean hasMoreSentences() {
        return sentenceBuffer.size() > 0;
    }
    public String nextSentence() {
        return sentenceBuffer.poll();
    }
}
