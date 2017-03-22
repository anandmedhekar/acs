package com.ge.predix.acs.service.policy.evaluation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ge.predix.acs.attribute.readers.SubjectAttributeReader;
import com.ge.predix.acs.model.Attribute;

public class SubjectAttributeResolver {

    private final Map<String, Set<Attribute>> subjectAttributeMap = new HashMap<>();
    private final SubjectAttributeReader subjectAttributeReader;
    private final String subjectIdentifier;
    private final Set<Attribute> supplementalSubjectAttributes;

    public SubjectAttributeResolver(final SubjectAttributeReader subjectAttributeReader, final String subjectIdentifier,
            final Set<Attribute> supplementalSubjectAttributes) {
        this.subjectAttributeReader = subjectAttributeReader;
        this.subjectIdentifier = subjectIdentifier;
        if (null == supplementalSubjectAttributes) {
            this.supplementalSubjectAttributes = Collections.emptySet();
        } else {
            this.supplementalSubjectAttributes = supplementalSubjectAttributes;
        }
    }

    public Set<Attribute> getResult(final Set<Attribute> scopes) {
        Set<Attribute> subjectAttributes = this.subjectAttributeMap.get(this.subjectIdentifier);
        if (null == subjectAttributes) {
            subjectAttributes = new HashSet<>(
                    this.subjectAttributeReader.getAttributesByScope(this.subjectIdentifier, scopes));
            subjectAttributes.addAll(this.supplementalSubjectAttributes);
            this.subjectAttributeMap.put(this.subjectIdentifier, subjectAttributes);
        }
        return subjectAttributes;
    }

}
