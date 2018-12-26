package com.amazon.jonnu.csgotracker.storage.hltv;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;

import com.google.inject.Guice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazon.jonnu.csgotracker.injection.module.HLTVModule;
import com.amazon.jonnu.csgotracker.model.Renderable;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.storage.hltv.parser.HLTVDocumentParser;

class HLTVDocumentParserFactoryImplTest {

    private HLTVDocumentParserFactory fixture;

    @BeforeEach
    void before() {
        fixture = Guice.createInjector(new HLTVModule())
                .getInstance(HLTVDocumentParserFactory.class);
    }

    @Test
    void getValidParser() {
        HLTVDocumentParser<TeamRoster> parser = fixture.getParser(TeamRoster.class);
        assertThat(parser, notNullValue());
    }

    @Test
    void getInvalidParser() {
        HLTVDocumentParser<DummyModel> parser = fixture.getParser(DummyModel.class);
        assertThat(parser, nullValue());
    }

    private class DummyModel implements Renderable {
    }
}
