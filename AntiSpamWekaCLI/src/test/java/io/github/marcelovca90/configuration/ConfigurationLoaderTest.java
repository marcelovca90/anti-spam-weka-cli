/*******************************************************************************
 * Copyright (C) 2018 Marcelo Vinícius Cysneiros Aragão
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package io.github.marcelovca90.configuration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationLoaderTest
{
    private final ClassLoader classLoader = getClass().getClassLoader();

    @InjectMocks
    private ConfigurationLoader configurationLoader;

    @Test
    public void load_defaultProperties_shouldReturnNotNullConfiguration() throws URISyntaxException
    {
        // when
        Configuration configuration = configurationLoader.load();

        // then
        assertThat(configuration, notNullValue());
    }

    @Test
    public void load_customValidProperties_shouldReturnNotNullConfiguration() throws URISyntaxException
    {
        // given
        String filename = Paths.get(classLoader.getResource("properties/valid.properties").toURI()).toFile().getAbsolutePath();

        // when
        Configuration configuration = configurationLoader.load(filename);

        // then
        assertThat(configuration, notNullValue());
        assertThat(configuration.getMetadataPath(), notNullValue());
        assertThat(configuration.getClassNamesOptionsAndLogNames(), notNullValue());
        assertThat(configuration.getClassNamesOptionsAndLogNames().size(), equalTo(2));
        assertThat(configuration.getRuns(), equalTo(10));
        assertThat(configuration.isTsneAnalysis(), equalTo(false));
        assertThat(configuration.isTsneAnalysis(), equalTo(false));
        assertThat(configuration.shouldLoadArff(), equalTo(false));
        assertThat(configuration.shouldShrinkFeatures(), equalTo(true));
        assertThat(configuration.shouldBalanceClasses(), equalTo(true));
        assertThat(configuration.shouldIncludeEmpty(), equalTo(true));
        assertThat(configuration.shouldSaveModel(), equalTo(false));
        assertThat(configuration.shouldSaveArff(), equalTo(false));
    }

    @Test
    public void load_customInvalidProperties_shouldReturnNullConfiguration() throws URISyntaxException
    {
        // given
        String filename = Paths.get(classLoader.getResource("properties/invalid.properties").toURI()).toFile().getAbsolutePath();

        // when
        Configuration configuration = configurationLoader.load(filename);

        // then
        assertThat(configuration, nullValue());
    }
}
