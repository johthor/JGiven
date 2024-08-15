package com.tngtech.jgiven.report.asciidoc;

import com.tngtech.jgiven.report.model.Tag;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TagHierarchyCalculator {
    private final Map<String, Tag> allTags;
    private final Map<String, List<String>> taggedScenarioFiles;

    public TagHierarchyCalculator(final Map<String, Tag> allTags, final Map<String, List<String>> taggedScenarioFiles) {
        this.allTags = allTags;
        this.taggedScenarioFiles = taggedScenarioFiles;
    }

    Map<TagGroup, List<Tag>> computeTagGroups() {
        return allTags.entrySet().stream()
                .collect(Collectors.groupingBy(
                        this::toTagGroup,
                        Collectors.mapping(Entry::getValue, Collectors.toList())));
    }

    private TagGroup toTagGroup(final Entry<String, Tag> entry) {
        final Tag tag = entry.getValue();

        return new TagGroup(tag.getFullType(), tag.getName());
    }

    public Map<String, Set<Tag>> computeTagHierarchy() {
        final Map<String, Set<Tag>> hierarchy = new HashMap<>();

        allTags.forEach((tagId, tag) -> {
            hierarchy.putIfAbsent(tagId, new HashSet<>());
            tag.getTags().forEach(parentId ->
                    hierarchy.computeIfAbsent(parentId, k -> new HashSet<>()).add(tag)
            );
        });

        return hierarchy;
    }

    static class TagGroup implements Comparable<TagGroup> {
        private final String fullType;
        private final String name;

        TagGroup(String fullType, String name) {
            this.fullType = fullType;
            this.name = name;
        }

        public String fullType() {
            return fullType;
        }

        public String name() {
            return name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TagGroup tagGroup = (TagGroup) o;
            return Objects.equals(fullType, tagGroup.fullType) && Objects.equals(name, tagGroup.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fullType, name);
        }

        @Override
        public int compareTo(final TagGroup o) {
            return Objects.compare(this.name, o.name, String::compareTo);
        }
    }
}
