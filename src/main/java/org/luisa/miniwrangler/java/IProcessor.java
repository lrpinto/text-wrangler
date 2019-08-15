package org.luisa.miniwrangler.java;

import java.io.IOException;

import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface IProcessor {

    /**
     * Close the file reader
     *
     * @throws IOException if an error occurs when closing the reader
     */
    void close() throws IOException;

    /**
     * Create the lexer
     *
     * @return the lexer
     */
    IProcessor createLexer();

    /**
     * Create the parser tree
     *
     * @return the parse tree
     */
    IProcessor createParserTree();

    /**
     * Return the listener
     *
     * @return the listener
     */
    ParseTreeListener getListener();

    /**
     * Determine which file should be processed
     *
     * @return this IProcessor with a specified file path
     */
    IProcessor processFile(String filePath);

    /**
     * Walk through the file
     *
     * @return this IProcessor after it has walked through the listener
     */
    IProcessor walk();

}