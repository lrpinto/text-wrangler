package org.luisa.miniwrangler.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.luisa.miniwrangler.TextTransformLexer;
import org.luisa.miniwrangler.TextTransformParser;
import org.luisa.miniwrangler.java.file.utils.FileUtils;
import org.luisa.miniwrangler.java.parsing.TextTransformCustomListener;

public class DslProcessor implements IProcessor {

    private static final Logger LOGGER = Logger.getLogger(DslProcessor.class.getName());
    Reader reader = null;
    TextTransformLexer lexer = null;
    ParseTree tree = null;
    private TextTransformCustomListener listener;

    /**
     * Create a new Text Transformation DSL Processor
     */
    public DslProcessor() {
        reader = new InputStreamReader(System.in);
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.close()
     *
     */
    @Override
    public void close() throws IOException {
        reader.close();
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.createLexer()
     *
     */
    @Override
    public IProcessor createLexer() {
        /* Create TextTransform Lexer */
        try {
            lexer = new TextTransformLexer(CharStreams.fromReader(reader));
        } catch (final IOException e) {
            LOGGER.warning("Create lexer failed: reason=" + e.getMessage());
        }
        return this;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.createParserTree()
     *
     */
    @Override
    public IProcessor createParserTree() {
        /* Parse TextTransform and Save Transformations */
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final TextTransformParser parser = new TextTransformParser(tokens);
        parser.setBuildParseTree(true);
        tree = parser.transformations();
        return this;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.getListener()
     *
     */
    @Override
    public ParseTreeListener getListener() {
        return listener;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.processFile()
     *
     */
    @Override
    public IProcessor processFile(String filePath) {
        try {
            reader = FileUtils.getBufferedReader(filePath);
        } catch (final FileNotFoundException e) {
            LOGGER.warning("Unable to read file: reason=" + e.getMessage());
        }
        return this;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.walk()
     *
     */
    @Override
    public IProcessor walk() {
        final ParseTreeWalker walker = new ParseTreeWalker();
        listener = new TextTransformCustomListener();
        walker.walk(listener, tree);
        return this;
    }

}
