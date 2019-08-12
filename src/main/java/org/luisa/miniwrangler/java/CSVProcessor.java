package org.luisa.miniwrangler.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.luisa.miniwrangler.java.parsing.CSVCustomListener;
import org.luisa.miniwrangler.java.parsing.TextTransformCustomListener;

public class CSVProcessor implements IProcessor {

	private static final Logger LOGGER = Logger.getLogger(CSVProcessor.class.getName());
	Reader reader = null;
	Lexer lexer = null;
	ParseTree tree = null;
	private final TextTransformCustomListener ttListener;
	private CSVCustomListener listener;

	public CSVProcessor(TextTransformCustomListener ttListener) {
		this.ttListener = ttListener;
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

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

	@Override
	public IProcessor createParserTree() {
		/* Parse CSV and Save Orders */
		final CommonTokenStream csvTokens = new CommonTokenStream(lexer);
		final CSVParser csvParser = new CSVParser(csvTokens);
		csvParser.setBuildParseTree(true);
		tree = csvParser.csvFile();
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
		listener = new CSVCustomListener(ttListener);
		walker.walk(listener, tree);
		return this;
	}
}
