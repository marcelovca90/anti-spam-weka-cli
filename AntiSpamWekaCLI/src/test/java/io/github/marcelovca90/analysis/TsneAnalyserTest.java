package io.github.marcelovca90.analysis;

import static org.mockito.Mockito.when;

import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.marcelovca90.data.DatasetMetadata;
import weka.core.Instances;
import weka.core.Utils;
import weka.datagenerators.classifiers.classification.RDG1;

@RunWith(MockitoJUnitRunner.class)
public class TsneAnalyserTest
{
    private final ClassLoader classLoader = getClass().getClassLoader();

    @Mock
    private DatasetMetadata metadata;

    @InjectMocks
    private TsneAnalyser tsneAnalyser;

    @Test
    public void run_withValidOutputFilename_shouldNotThrowException() throws Exception
    {
        // given
        String filename = Paths.get(classLoader.getResource("dataset/method/8/data.arff").toURI()).toFile().getAbsolutePath();
        when(metadata.getTsneFilename()).thenReturn(FilenameUtils.separatorsToSystem(filename).replace("data.arff", "t-SNE.png"));

        // when
        tsneAnalyser.run(metadata, generateArtificialDataset(), true);
    }

    @Test(expected = Exception.class)
    public void run_withInvalidOutputFilename_shouldThrowException() throws Exception
    {
        // given
        when(metadata.getTsneFilename()).thenReturn(null);

        // when
        tsneAnalyser.run(metadata, generateArtificialDataset(), false);

        // then throw exception
    }

    private Instances generateArtificialDataset() throws Exception
    {
        RDG1 rdg1 = new RDG1();

        rdg1.setOptions(Utils.splitOptions("-r weka.datagenerators.classifiers.classification.RDG1-S_1_-n_100_-a_10_-c_2_-N_0_-I_0_-M_1_-R_10 -S 1 -n 100 -a 10 -c 2 -N 0 -I 0 -M 1 -R 10"));
        rdg1.defineDataFormat();

        return rdg1.generateExamples();
    }
}