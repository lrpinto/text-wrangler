package org.luisa.miniwrangler.java;

import java.io.IOException;
import java.sql.SQLException;

import org.luisa.miniwrangler.java.parsing.TextTransformCustomListener;

/**
 * App.java Shows how to use this library including:
 * - Loading the configuration properties 
 * - Processing a transformations DSL 
 * - Processing a CSV file
 *
 * @author Luisa Pinto
 */
public class App {

	public static void main(String[] args) throws IOException, SQLException {

		/* Initialise Context */
		Configuration.init();

		/* Process Transformations */
		final String dslFilename = Configuration.getProperty("dsl.filename");

		final IProcessor dslProcessor = new DslProcessor()
				.processFile(dslFilename)
				.createLexer()
				.createParserTree()
				.walk();

		dslProcessor.close();

		/* Process CSV file */
		final TextTransformCustomListener listener = (TextTransformCustomListener) dslProcessor.getListener();

		final String csvFilename = Configuration.getProperty("csv.filename");

		final int bulkSaveSize = Configuration.getProperty("csv.bulk.save.size", 50);
		final boolean replaceFl = Configuration.getProperty("csv.replace.fl", 0) == 1;
		final IProcessor csvProcessor = new CSVProcessor(listener, bulkSaveSize, replaceFl)
				.processFile(csvFilename)
				.createLexer()
				.createParserTree()
				.walk();

		csvProcessor.close();
	}

}
