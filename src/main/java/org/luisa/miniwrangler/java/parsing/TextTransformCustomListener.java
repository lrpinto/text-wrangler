package org.luisa.miniwrangler.java.parsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.luisa.miniwrangler.TextTransformParser.ConcatContext;
import org.luisa.miniwrangler.TextTransformParser.DelimeterContext;
import org.luisa.miniwrangler.TextTransformParser.FixedValueContext;
import org.luisa.miniwrangler.TextTransformParser.OrderFieldContext;
import org.luisa.miniwrangler.TextTransformParser.PatternContext;
import org.luisa.miniwrangler.TextTransformParser.RenameContext;
import org.luisa.miniwrangler.TextTransformParser.SrcFieldContext;
import org.luisa.miniwrangler.TextTransformParser.TransformationContext;
import org.luisa.miniwrangler.TextTransformParser.TransformationsContext;
import org.luisa.miniwrangler.TextTransformParser.ValueContext;
import org.luisa.miniwrangler.TextTransformParserBaseListener;
import org.luisa.miniwrangler.java.data.utils.StringUtils;
import org.luisa.miniwrangler.java.transform.ITextTransform;
import org.luisa.miniwrangler.java.transform.ITextTransformBuilder;
import org.luisa.miniwrangler.java.transform.ITextTransformConcatBuilder;
import org.luisa.miniwrangler.java.transform.ITextTransformFixedValueBuilder;
import org.luisa.miniwrangler.java.transform.ITextTransformRenameBuilder;
import org.luisa.miniwrangler.java.transform.TextTransformConcat;
import org.luisa.miniwrangler.java.transform.TextTransformFixedValue;
import org.luisa.miniwrangler.java.transform.TextTransformRename;

/**
 * TextTransformCustomListener.java
 *
 * Class that listens to a transformation DSL parser and builds the specified
 * transformations depending on the rule being parsed
 *
 * @author Luisa Pinto
 *
 */
public class TextTransformCustomListener extends TextTransformParserBaseListener {

    private static final Logger LOGGER = Logger.getLogger(TextTransformCustomListener.class.getName());

    private final List<ITextTransform> transformations = new ArrayList<>();

    private ITextTransformBuilder builder = null;
    private final Set<String> srcFields = new HashSet<>();

    /**
     * On entering a 'concat' rule create a new TextTransformConcat builder
     */
    @Override
    public void enterConcat(ConcatContext ctx) {
        super.enterConcat(ctx);
        builder = new TextTransformConcat.Builder();

    }

    /**
     * On entering the a 'delimeter' rule set the delimiter for the a 'concat'
     * transformation
     */
    @Override
    public void enterDelimeter(DelimeterContext ctx) {
        super.enterDelimeter(ctx);
        ((ITextTransformConcatBuilder) builder).with(ctx.getText());
    }

    /**
     * On entering a 'fixedValue' rule create a new TextTransformFixedValue builder
     */
    @Override
    public void enterFixedValue(FixedValueContext ctx) {
        super.enterFixedValue(ctx);
        builder = new TextTransformFixedValue.Builder();
    }

    /**
     * On entering a order field set it as target field on the transformation being
     * built
     */
    @Override
    public void enterOrderField(OrderFieldContext ctx) {
        super.enterOrderField(ctx);
        builder.as(ctx.getText());
    }

    /**
     * On entering a pattern set it as expected pattern on the transformation being
     * built
     */
    @Override
    public void enterPattern(PatternContext ctx) {
        super.enterPattern(ctx);
        builder.match(ctx.getText());
    }

    /**
     * On entering a 'rename' rule create a new TextTransformRename builder
     */
    @Override
    public void enterRename(RenameContext ctx) {
        super.enterRename(ctx);
        builder = new TextTransformRename.Builder();
    }

    /**
     * On entering a source field set it as source field on the transformation being
     * built
     */
    @Override
    public void enterSrcField(SrcFieldContext ctx) {
        super.enterSrcField(ctx);
        String srcField = ctx.getText();

        // Strip quotes if needed
        srcField = StringUtils.stripQuotes(srcField);

        if (builder instanceof ITextTransformRenameBuilder) {
            ((ITextTransformRenameBuilder) builder).rename(srcField);
        } else if (builder instanceof ITextTransformConcatBuilder) {
            ((ITextTransformConcatBuilder) builder).concat(srcField);
        }
    }

    /**
     * On entering a value set it has the value for a 'fixedValue' transformation
     */
    @Override
    public void enterValue(ValueContext ctx) {
        super.enterValue(ctx);
        final String value = ctx.getText();
        ((ITextTransformFixedValueBuilder) builder).fixedValue(value);
    }

    /**
     * On exiting a transformation rule adds the transformation to the list of
     * transformations and the source its source field to the set of source fields
     */
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

    /**
     * On exiting th transformations dsl file log the number of transformations
     * created
     */
    @Override
    public void exitTransformations(TransformationsContext ctx) {
        super.enterTransformations(ctx);
        LOGGER.info("Text transformations created: " + transformations.size() + "\n" + transformations);
    }

    /**
     * Return source fields
     * 
     * @return a list of source fields
     */
    public Set<String> getSrcFields() {
        return srcFields;
    }

    /**
     * Return transformations
     * 
     * @return a list with transformations
     */
    public Collection<ITextTransform> getTransformations() {
        return transformations;
    }
}
