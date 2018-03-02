package groovy

import groovy.partner.elong.ELongTest
import groovy.partner.elong.H5Test
import groovy.partner.ly.LyTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite.class)
@Suite.SuiteClasses([
        H5Test.class,
        LyTest.class,
        ELongTest.class

])

public class TestSuite {
}
