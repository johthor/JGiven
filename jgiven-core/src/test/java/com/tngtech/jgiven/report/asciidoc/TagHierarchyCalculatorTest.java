package com.tngtech.jgiven.report.asciidoc;

import static org.assertj.core.api.Assertions.assertThat;

import com.tngtech.jgiven.report.asciidoc.TagHierarchyCalculator.TagGroup;
import com.tngtech.jgiven.report.model.Tag;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class TagHierarchyCalculatorTest {

    private final Tag tag1 = new Tag("org.example.Tag1");
    private final Tag tag2 = new Tag("org.example.Tag2");
    private final Tag tag3 = new Tag("org.example.Tag2");


    @Test
    public void compute_empty_groups() {
        // given
        final Map<String, Tag> allTags = Map.of(tag1.toIdString(), tag1);
        final Map<String, List<String>> taggedScenarioFiles = Map.of();
        final TagHierarchyCalculator calculator = new TagHierarchyCalculator(allTags, taggedScenarioFiles);

        // when
        final Map<TagGroup, List<Tag>> groupedTags = calculator.computeTagGroups();

        // then
        assertThat(groupedTags).isEmpty();

    }

    @Test
    public void computeGroupedTag() {
        // given
        final Map<String, Tag> allTags = Map.of(tag1.toIdString(), tag1);
        final Map<String, List<String>> taggedScenarioFiles = Map.of(tag1.toIdString(), List.of("feature1"));
        final TagHierarchyCalculator calculator = new TagHierarchyCalculator(allTags, taggedScenarioFiles);

        // when
        final Map<TagGroup, List<Tag>> groupedTags = calculator.computeTagGroups();

        // then
//        assertThat(groupedTags).containsEntry(new TagHierarchyCalculator.TagGroup());

    }

    @Test
    public void compute_empty_hierarchy() {
        // given
        final Map<String, Tag> allTags = Map.of(tag1.toIdString(), tag1);
        final Map<String, List<String>> taggedScenarioFiles = Map.of();
        final TagHierarchyCalculator calculator = new TagHierarchyCalculator(allTags, taggedScenarioFiles);

        // when
        final Map<String, Set<Tag>> hierarchy = calculator.computeTagHierarchy();

        // then
        assertThat(hierarchy).containsEntry("org.example.Tag1", Set.of());

    }
}
