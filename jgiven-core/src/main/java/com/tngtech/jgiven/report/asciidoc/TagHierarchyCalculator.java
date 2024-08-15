package com.tngtech.jgiven.report.asciidoc;

import com.tngtech.jgiven.report.model.Tag;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagHierarchyCalculator {
    private final Map<String, List<String>> taggedScenarioFiles;
    private final Map<String, Tag> allTags;

    public TagHierarchyCalculator(final Map<String, Tag> allTags, final Map<String, List<String>> taggedScenarioFiles) {
        this.taggedScenarioFiles = taggedScenarioFiles;
        this.allTags = allTags;
    }

    SortedMap<TagGroup, Map<Tag, List<String>>> computeTagGroups() {
        final Stream<Entry<String, List<String>>> entryStream = taggedScenarioFiles.entrySet().stream()
                .filter(entry -> allTags.get(entry.getKey()).getShownInNavigation());
        return entryStream
                .collect(Collectors.groupingBy(
                        this::tagGroup,
                        () -> new TreeMap<>(Comparator.comparing(TagGroup::name)),
                        Collectors.toMap(entry -> allTags.get(entry.getKey()), Entry::getValue)));
    }

    private TagGroup tagGroup(final Entry<String, List<String>> entry) {
        final Tag tag = allTags.get(entry.getKey());

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
