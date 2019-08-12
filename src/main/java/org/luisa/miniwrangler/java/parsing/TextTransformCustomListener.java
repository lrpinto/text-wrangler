package org.luisa.miniwrangler.java.parsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.luisa.miniwrangler.TextTransformParser.ConcatContext;
import org.luisa.miniwrangler.TextTransformParser.FixedValueContext;
import org.luisa.miniwrangler.TextTransformParser.OrderFieldContext;
import org.luisa.miniwrangler.TextTransformParser.PatternContext;
import org.luisa.miniwrangler.TextTransformParser.RenameContext;
import org.luisa.miniwrangler.TextTransformParser.SrcFieldContext;
import org.luisa.miniwrangler.TextTransformParser.TransformationContext;
import org.luisa.miniwrangler.TextTransformParser.TransformationsContext;
import org.luisa.miniwrangler.TextTransformParser.ValueContext;
import org.luisa.miniwrangler.TextTransformParserBaseListener;
import org.luisa.miniwrangler.java.transform.ITextTransform;
import org.luisa.miniwrangler.java.transform.ITextTransformBuilder;
import org.luisa.miniwrangler.java.transform.ITextTransformConcatBuilder;
import org.luisa.miniwrangler.java.transform.ITextTransformFixedValueBuilder;
import org.luisa.miniwrangler.java.transform.ITextTransformRenameBuilder;
import org.luisa.miniwrangler.java.transform.TextTransformConcat;
import org.luisa.miniwrangler.java.transform.TextTransformFixedValue;
import org.luisa.miniwrangler.java.transform.TextTransformRename;

public class TextTransformCustomListener extends TextTransformParserBaseListener {

	private static final Logger LOGGER = Logger.getLogger(TextTransformCustomListener.class.getName());

	private final List<ITextTransform> transformations = new ArrayList<>();
	private ITextTransformBuilder builder = null;
	private final Set<String> srcFields = new HashSet<>();

	@Override
	public void enterConcat(ConcatContext ctx) {
		super.enterConcat(ctx);
		builder = new TextTransformConcat.Builder();

	}

	@Override
	public void enterFixedValue(FixedValueContext ctx) {
		super.enterFixedValue(ctx);
		builder = new TextTransformFixedValue.Builder();
	}

	@Override
	public void enterOrderField(OrderFieldContext ctx) {
		super.enterOrderField(ctx);
		builder.as(ctx.getText());
	}

	@Override
	public void enterPattern(PatternContext ctx) {
		super.enterPattern(ctx);
		builder.match(ctx.getText());
	}

	@Override
	public void enterRename(RenameContext ctx) {
		super.enterRename(ctx);
		builder = new TextTransformRename.Builder();
	}

	@Override
	public void enterSrcField(SrcFieldContext ctx) {
		super.enterSrcField(ctx);
		String srcField = ctx.getText();

		// Strip quotes if needed
		final char start = srcField.charAt(0);
		final char end = srcField.charAt(srcField.length() - 1);
		if (start == '\'' || start == '\"' || start == '`' && start == end) {
			srcField = srcField.substring(1, srcField.length() - 1);
		}

		if (builder instanceof ITextTransformRenameBuilder) {
			((ITextTransformRenameBuilder) builder).rename(srcField);
		} else if (builder instanceof ITextTransformConcatBuilder) {
			((ITextTransformConcatBuilder) builder).concat(srcField);
		}
	}

	@Override
	public void enterValue(ValueContext ctx) {
		super.enterValue(ctx);
		final String value = ctx.getText();
		((ITextTransformFixedValueBuilder) builder).fixedValue(value);
	}

	@Override
	public void exitTransformation(TransformationContext ctx) {
		super.exitTransformation(ctx);
		final ITextTransform transformation = builder.build();
		transformations.add(transformation);
		if (transformation instanceof TextTransformRename) {
			final String srcField = ((TextTransformRename) transformation).getSrcField();
			srcFields.add(srcField);
		} else if (transformation instanceof TextTransformConcat) {
			final List<String> fields = ((TextTransformConcat) transformation).getSrcFields();
			srcFields.addAll(fields);
		}
	}

	@Override
	public void exitTransformations(TransformationsContext ctx) {
		super.enterTransformations(ctx);
		LOGGER.info("Text transformations created: " + transformations.size() + "\n" + transformations);
	}

	public Set<String> getSrcFields() {
		return srcFields;
	}

	public Collection<ITextTransform> getTransformations() {
		return transformations;
	}
}
