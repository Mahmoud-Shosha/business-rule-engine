package org.gs1eg.business_rule_engine.rule.domain.service.impl;

import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;
import org.gs1eg.business_rule_engine.rule.domain.repo.RuleRepo;
import org.gs1eg.business_rule_engine.shared.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleServiceImplTest {

    @Mock
    private RuleRepo repo;

    @InjectMocks
    private RuleServiceImpl ruleService;

    private static List<Rule> mockRules;

    @BeforeEach
    void setup() {
        mockRules = RuleTestDataFactory.getRules();
    }

    @Test
    void findAll_returnsPageOfRules() {
        // Given
        when(repo.findAll(0, 10)).thenReturn(new PageImpl<>(mockRules));
        // When
        Page<Rule> rulesPage = ruleService.findAll(0, 10);
        // Then
        assertEquals(mockRules.size(), rulesPage.getNumberOfElements());
        assertEquals("Credit Risky Transaction", rulesPage.getContent().get(0).getName());
        assertEquals("International Transfer", rulesPage.getContent().get(1).getName());
    }

    @Test
    void findById_entityExists_returnsEntity() {
        // Given
        Rule existingRule = mockRules.getFirst();
        UUID id = existingRule.getId();
        when(repo.findById(id)).thenReturn(Optional.of(existingRule));
        // When
        Optional<Rule> result = ruleService.findById(id);
        // Then
        assertTrue(result.isPresent());
        assertEquals(existingRule, result.get());
    }

    @Test
    void findById_entityDoesNotExist_returnsEmpty() {
        // Given
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());
        // When
        Optional<Rule> result = ruleService.findById(id);
        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void save_validRule_savesSuccessfully() {
        // Given
        Rule newRule = RuleTestDataFactory.getNewRule();
        when(repo.save(newRule)).thenReturn(newRule);
        // When
        Rule savedRule = ruleService.save(newRule);
        // Then
        assertNotNull(savedRule);
        assertEquals(newRule, savedRule);
    }

    @Test
    void update_existingRule_updatesSuccessfully() {
        // Given
        Rule existingRule = mockRules.getFirst();
        existingRule.setName("<UPDATED>");
        when(repo.update(existingRule)).thenReturn(existingRule);
        // When
        Rule updatedRule = ruleService.update(existingRule);
        // Then
        assertNotNull(updatedRule);
        assertEquals("<UPDATED>", updatedRule.getName());
    }

    @Test
    void update_nonExistingRule_throwsException() {
        // Given
        Rule nonExistingRule = mockRules.getFirst();
        nonExistingRule.setName("<UPDATED>");
        when(repo.update(nonExistingRule)).thenThrow(new EntityNotFoundException());
        // When & Then
        assertThrows(EntityNotFoundException.class, () -> ruleService.update(nonExistingRule));
    }

    @Test
    void deleteById_existingId_deletesSuccessfully() {
        // Given
        Rule existingRule = mockRules.getFirst();
        UUID id = existingRule.getId();
        // When & Then
        assertDoesNotThrow(() -> ruleService.deleteById(id));
    }

    @Test
    void findByTypeOrderByPriorityAsc_enrichmentType_returnsEnrichmentRules() {
        // Given
        RuleType type = RuleType.ENRICHMENT;
        List<Rule> rulesList = mockRules.stream()
                .filter(rule -> rule.getType().equals(type))
                .sorted(Comparator.comparing(Rule::getPriority))
                .toList();
        when(repo.findByTypeOrderByPriorityAsc(type)).thenReturn(rulesList);
        // When
        List<Rule> result = ruleService.findByTypeOrderByPriorityAsc(type);
        // Then
        assertNotNull(result);
        assertEquals(rulesList.size(), result.size());
        assertEquals(rulesList, result);
    }

    @Test
    void findByTypeOrderByPriorityAsc_routingType_returnsRoutingRules() {
        // Given
        RuleType type = RuleType.ROUTING;
        List<Rule> rulesList = mockRules.stream()
                .filter(rule -> rule.getType().equals(type))
                .sorted(Comparator.comparing(Rule::getPriority))
                .toList();
        when(repo.findByTypeOrderByPriorityAsc(type)).thenReturn(rulesList);
        // When
        List<Rule> result = ruleService.findByTypeOrderByPriorityAsc(type);
        // Then
        assertNotNull(result);
        assertEquals(rulesList.size(), result.size());
        assertEquals(rulesList, result);
    }

}
