package ru.otus.homework.homework

import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.jupiter.api.Assertions.*

class ProcessListTest {
    @Test
    fun testSkipThreeAndPrint() {
        val output = ByteArrayOutputStream()
        val originalOutput = System.out
        try {
            System.setOut(PrintStream(output))
            skipThreeAndPrint(listOf(1, 2, 3, 4))
        } finally {
            System.setOut(originalOutput)
        }

        val expected = "Processing 1\nProcessing 2\nProcessing 4\n"
        assertEquals(expected, output.toString())
    }

    @Test
    fun `skips third position not value three`() {
        val output = ByteArrayOutputStream()
        val originalOutput = System.out
        try {
            System.setOut(PrintStream(output))
            skipThreeAndPrint(listOf(3, 10, 20, 3))
        } finally {
            System.setOut(originalOutput)
        }
        assertEquals("Processing 3\nProcessing 10\nProcessing 3\n", output.toString())
    }
}
