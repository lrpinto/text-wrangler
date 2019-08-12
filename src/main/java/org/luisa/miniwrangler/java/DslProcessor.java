package org.luisa.miniwrangler.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.luisa.miniwrangler.java.parsing.TextTransformCustomListener;

public class DslProcessor implements IProcessor {

	private static final Logger LOGGER = Logger.getLogger(DslProcessor.class.getName());
	Reader reader = null;
	TextTransformLexer lexer = null;
	ParseTree tree = null;
	private TextTransformCustomListener listener;

	public DslProcessor() {
		reader = new InputStreamReader(System.in);
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

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

	@Override
	public IProcessor createParserTree() {
		/* Parse TextTransform and Save Transformations */
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final TextTransformParser parser = new TextTransformParser(tokens);
		parser.setBuildParseTree(true);
		tree = parser.transformations();
		return this;
	}

	private Reader getBufferedReader(String filePath) {
		if (filePath != null) {
			try {
				reader = new BufferedReader(new FileReader(filePath));
			} catch (final FileNotFoundException e) {
				LOGGER.warning("Unable to read file: reason=" + e.getMessage());
			}
		}
		return reader;
	}

	@Override
	public ParseTreeListener getListener() {
		return listener;
	}

	@Override
	public IProcessor processFile(String filePath) {
		reader = getBufferedReader(filePath);
		return this;
	}

	@Override
	public IProcessor walk() {
		final ParseTreeWalker walker = new ParseTreeWalker();
		listener = new TextTransformCustomListener();
		walker.walk(listener, tree);
		return this;
	}

}
