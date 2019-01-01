package com.amazon.jonnu.csgotracker.service;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class TemplateReplacerImpl implements TemplateReplacer {

    // @TODO - do we need the 'type' piece? seems YAGNI...
    private static final Pattern PATTERN = Pattern.compile("\\$\\{((?<type>[a-z]+):)?(?<key>[a-z]+)}");
    private final Map<Class, TemplateVariableReplacer> replacementStrategies;

    @Inject
    public TemplateReplacerImpl(@NonNull final Map<Class, TemplateVariableReplacer> replacementStrategies) {
        this.replacementStrategies = replacementStrategies;
    }

    @Override
    public String replace(@NonNull final String template, @NonNull final Map<String, TemplateVariable> replacements) {

        if (StringUtils.isBlank(template)) {
            return template;
        }

        Matcher matcher = PATTERN.matcher(template);
        StringBuilder mutable = new StringBuilder(template);

        LinkedList<TemplateReplacement> results = new LinkedList<>();

        while (matcher.find()) {

            final TemplateVariable replacement = replacements.getOrDefault(matcher.group("key"), SimpleTemplateVariable.EMPTY);

            if (replacement.isEmpty()) {
                // do something?
                log.error("Replacement {} = empty", replacement.getKey());
            }

            if (!replacementStrategies.containsKey(replacement.getType())) {
                log.error("No replacement strategy for type: {}", replacement.getType().getSimpleName());
                continue;
            }

            @SuppressWarnings("unchecked")
            String rep = replacementStrategies.get(replacement.getType())
                    .replace(replacement);

            results.addFirst(TemplateReplacement.builder()
                    .start(matcher.start())
                    .end(matcher.end())
                    .replacement(rep)
                    .build());
        }

        results.forEach(replacement -> mutable.replace(replacement.getStart(), replacement.getEnd(), replacement.getReplacement()));
        return StringUtils.normalizeSpace(mutable.toString());
    }

}
