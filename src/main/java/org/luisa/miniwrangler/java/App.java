package org.luisa.miniwrangler.java;

import java.io.IOException;
import java.sql.SQLException;

import org.luisa.miniwrangler.java.parsing.TextTransformCustomListener;

public class App {

	public static void main(String[] args) throws IOException, SQLException {

		/* Initialise Context */
		Context.init();

		/* Process Transformations */
		final String dslFilename = Context.getConfigAsString("dsl.filename");

		final IProcessor dslProcessor = new DslProcessor()
				.processFile(dslFilename)
				.createLexer()
				.createParserTree()
				.walk();

		dslProcessor.close();

		/* Process CSV file */
		final TextTransformCustomListener listener = (TextTransformCustomListener) dslProcessor.getListener();

		final String csvFilename = Context.getConfigAsString("csv.filename");

		final IProcessor csvProcessor = new CSVProcessor(listener)
				.processFile(csvFilename)
				.createLexer()
				.createParserTree()
				.walk();

		csvProcessor.close();
	}

}
