package org.luisa.miniwrangler.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.luisa.miniwrangler.CSVLexer;
import org.luisa.miniwrangler.CSVParser;
import org.luisa.miniwrangler.java.file.utils.FileUtils;
import org.luisa.miniwrangler.java.parsing.CSVCustomListener;
import org.luisa.miniwrangler.java.parsing.TextTransformCustomListener;

/*
 * TODO:
 * - Should refactor this class to separate the responsability of building the CSVCustomListener and walk through it
 * - In addition should make it able to listen to updates from the CSVCustomerListener,
 * such as exitRow, exitHeader so that it can react to them by creating a new map of srcField,
 * value pairs and subsequently update an instance of ITextTransformRunner<Facade> with this map
 *
 * Basically, looking at a Pub-Sub/Pub-Sub architecture involving the classes CSVCustomerListner-CSVProcessor-ITextTransformRunner

 */

/**
 * Class responsible to process a CSV file - Given a list of text
 * transformations <br>
 * - builds and executes the CSV parser, lexer and listener<br>
 *
 * @author Luisa Pinto
 *
 */
public class CSVProcessor implements IProcessor {

    public static final int BULK_SAVE_SZ_DF = 1;

    private static final Logger LOGGER = Logger.getLogger(CSVProcessor.class.getName());

    private final int bulkSaveSize;

    Lexer lexer = null;

    private CSVCustomListener listener;

    Reader reader = null;

    private boolean replaceFl;

    ParseTree tree = null;
    private final TextTransformCustomListener ttListener;

    /**
     * Create a CSV processor with the given Transformation Listener
     *
     * @param ttListener the given transformation listener
     */
    public CSVProcessor(TextTransformCustomListener ttListener) {
        this.ttListener = ttListener;
        bulkSaveSize = BULK_SAVE_SZ_DF; // default
    }

    /**
     * Create a CSV processor with the given Transformation Listener, and an
     * additional parameter determining bulk save size
     *
     * @param ttListener the given transformation listener
     */
    public CSVProcessor(TextTransformCustomListener ttListener, int bulkSaveSize) {
        this.ttListener = ttListener;
        this.bulkSaveSize = bulkSaveSize;
    }

    /**
     * Create a CSV processor with the given Transformation Listener, an additional
     * parameter determining bulk save size, and an additional parameter determining
     * whether to replace duplicated entries in database
     *
     * @param ttListener the given transformation listener
     */
    public CSVProcessor(TextTransformCustomListener ttListener, int bulkSaveSize, boolean replaceFl) {
        this.ttListener = ttListener;
        this.bulkSaveSize = bulkSaveSize;
        this.replaceFl = replaceFl;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.close()
     */
    @Override
    public void close() throws IOException {
        reader.close();
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.createLexer()
     */
    @Override
    public IProcessor createLexer() {
        /* Create CSV lexer */
        try {
            lexer = new CSVLexer(CharStreams.fromReader(reader));
        } catch (final IOException e) {
            LOGGER.severe("Failed to create CSV Lexer: reason=" + e.getMessage());
            System.exit(0);
        }
        return this;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.createParserTree()
     */
    @Override
    public IProcessor createParserTree() {
        /* Parse CSV and Save Orders */
        final CommonTokenStream csvTokens = new CommonTokenStream(lexer);
        final CSVParser csvParser = new CSVParser(csvTokens);
        csvParser.setBuildParseTree(true);
        tree = csvParser.csvFile();
        return this;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.getListener()
     */
    @Override
    public ParseTreeListener getListener() {
        return listener;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.IProcessor.processFile()
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
     */
    @Override
    public IProcessor walk() {
        final ParseTreeWalker walker = new ParseTreeWalker();
        listener = new CSVCustomListener(ttListener);
        listener.setBulkSaveSize(bulkSaveSize);
        listener.setReplaceFl(replaceFl);
        walker.walk(listener, tree);
        return this;
    }
}
