package org.luisa.miniwrangler.java;

import java.io.IOException;

import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface IProcessor {

	void close() throws IOException;

	IProcessor createLexer();

	IProcessor createParserTree();

	ParseTreeListener getListener();

	IProcessor processFile(String filePath);

	IProcessor walk();

}