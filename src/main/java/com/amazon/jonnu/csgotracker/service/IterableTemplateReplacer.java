package com.amazon.jonnu.csgotracker.service;

import java.util.Iterator;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
public class IterableTemplateReplacer implements TemplateVariableReplacer<Iterable<String>> {

    private final String delimiter;
    private final String penultimator;
    private final boolean useOxfordDelimiter;

    public IterableTemplateReplacer() {
        this(",", "and", true);
    }

    public IterableTemplateReplacer(final String delimiter) {
        this(delimiter, "and", true);
    }

    public IterableTemplateReplacer(final String delimiter, final String penultimator) {
        this(delimiter, penultimator, true);
    }

    public IterableTemplateReplacer(final String delimiter, final String penultimator, final boolean useOxfordDelimiter) {
        this.delimiter = delimiter;
        this.penultimator = penultimator;
        this.useOxfordDelimiter = useOxfordDelimiter;
    }

    @Override
    public String replace(final TemplateVariable<Iterable<String>> data) {

        Iterator<String> iterator = data.getVariable().iterator();
        StringBuilder outputBuffer = new StringBuilder();

        while (iterator.hasNext()) {

            String current = iterator.next();
            boolean hasNext = iterator.hasNext();
            boolean isFirst = outputBuffer.length() == 0;

            if (isFirst) {
                outputBuffer.append(current);
                continue;
            }

            if (hasNext || useOxfordDelimiter) {
                outputBuffer.append(StringUtils.deleteWhitespace(delimiter));
                outputBuffer.append(StringUtils.SPACE);
            }

            if (!hasNext) {
                outputBuffer.append(useOxfordDelimiter ? StringUtils.EMPTY : StringUtils.SPACE)
                        .append(StringUtils.deleteWhitespace(penultimator))
                        .append(StringUtils.SPACE);
            }

            outputBuffer.append(current);
        }

        return outputBuffer.toString();
    }
}
