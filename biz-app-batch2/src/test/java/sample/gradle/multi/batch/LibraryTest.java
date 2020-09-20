package sample.gradle.multi.batch;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

class LibraryTest {

    @Test
    public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
    }

}
