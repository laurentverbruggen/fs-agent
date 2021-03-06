package org.whitesource.fs.configuration;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.whitesource.agent.dependency.resolver.npm.TestHelper;
import org.whitesource.fs.FSAConfiguration;

import java.io.*;
import java.util.Properties;

public class ConfigurationValidationTest {

    @Test
    public void shouldWorkWithProjectPerFolder() throws IOException {

        File tmpPath = TestHelper.getTempFileWithReplace("#projectPerFolder=true", "projectPerFolder=true");

        // act
        Properties configProperties = FSAConfiguration.readWithError(tmpPath.toString(), "").getKey();

        // assert
        Assert.assertNotNull(configProperties);
    }

    @Test
    public void shouldNotOverrideParametersFromCommandArgs() throws IOException {
        // arrange
        File tmpPath = TestHelper.getTempFileWithReplace("#projectPerFolder=true", "projectPerFolder=true");

        // act
        String[] commandLineArgs = new String[]{"-c", tmpPath.getAbsolutePath(), "-d", new File(TestHelper.FOLDER_WITH_MIX_FOLDERS).getAbsolutePath()};
        FSAConfiguration fsaConfiguration = new FSAConfiguration(commandLineArgs);

        // assert
        Assert.assertTrue(fsaConfiguration.getRequest().isProjectPerSubFolder());

        // assert
        Assert.assertTrue(fsaConfiguration.getRequest().isProjectPerSubFolder());

        // assert
        Assert.assertTrue(StringUtils.isEmpty(fsaConfiguration.getRequest().getProductName()));

        // act
        commandLineArgs = new String[]{"-apiKey", "token" , "-product", "productName"};
        fsaConfiguration = new FSAConfiguration(commandLineArgs);

        // assert
        //Assert.assertTrue(fsaConfiguration.getErrors().size()>0);
        // assert
        Assert.assertEquals("productName",fsaConfiguration.getRequest().getProductName());
    }

}
