package ua.nure.ipz.zoo.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.nure.ipz.zoo.controller.constant.SessionKeys;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

    private static final String MENU_URL = "/menu/";
    private static final String SET_LOCALE_URL = "/setlocale/";
    private static final String LANGUAGE_ATTRIBUTE = "language";
    private static final String COUNTRY_ATTRIBUTE = "country";
    private static final String RU_UPPER_CASE = "RU";
    private static final String RU = "ru";
    private static final String UK = "uk";
    private static final String UA = "UA";
    private static final String EN = "en";
    private static final String EMPTY_STRING = "";
    private static final String UNKNOWN_LOCALE = "hr";
    @InjectMocks
    private IndexController indexController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    public void shouldRedirectToMenu_whenGoOnRootMapping() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(redirectedUrl(MENU_URL));
    }

    @Test
    public void shouldSetDefaultLocale_whenNoLanguageSpecified() throws Exception {
        this.mockMvc.perform(post(SET_LOCALE_URL)).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSetRuLocale_whenSwitchRuToLocale() throws Exception {
        this.mockMvc.perform(post(SET_LOCALE_URL)
                .param(LANGUAGE_ATTRIBUTE, RU))
                .andExpect(status().isOk())
                .andExpect(request()
                .sessionAttribute(SessionKeys.LOCALE, is(allOf(
                        hasProperty(LANGUAGE_ATTRIBUTE, is(RU)),
                        hasProperty(COUNTRY_ATTRIBUTE, is(RU_UPPER_CASE))))));
    }

    @Test
    public void shouldSetUkLocale_whenSwitchUkToLocale() throws Exception {
        this.mockMvc.perform(post(SET_LOCALE_URL)
                .param(LANGUAGE_ATTRIBUTE, UK))
                .andExpect(status().isOk())
                .andExpect(
                request().sessionAttribute(SessionKeys.LOCALE, is(allOf(
                        hasProperty(LANGUAGE_ATTRIBUTE, is(UK)),
                        hasProperty(COUNTRY_ATTRIBUTE, is(UA))))));
    }

    @Test
    public void shouldSetEnLocale_whenSwitchEnToLocale() throws Exception {
        this.mockMvc.perform(post(SET_LOCALE_URL)
                .param(LANGUAGE_ATTRIBUTE, EN))
                .andExpect(status().isOk())
                .andExpect(request()
                .sessionAttribute(SessionKeys.LOCALE, is(allOf(
                        hasProperty(LANGUAGE_ATTRIBUTE, is(EN)),
                        hasProperty(COUNTRY_ATTRIBUTE, is(EMPTY_STRING))))));
    }

    @Test
    public void shouldSetDefaultLocale_whenSwitchToUnknownLocale() throws Exception {
        this.mockMvc.perform(post(SET_LOCALE_URL)
                .param(LANGUAGE_ATTRIBUTE, UNKNOWN_LOCALE))
                .andExpect(status().isOk())
                .andExpect(request()
                .sessionAttribute(SessionKeys.LOCALE, is(allOf(
                        hasProperty(LANGUAGE_ATTRIBUTE, is(EN)),
                        hasProperty(COUNTRY_ATTRIBUTE, is(EMPTY_STRING))))));
    }
}
