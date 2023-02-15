package com.example.assignment3
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import androidx.test.annotation.UiThreadTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.assignment3", appContext.packageName)
    }
}

@RunWith(AndroidJUnit4::class)
class FlashCardActivityTest {

    @UiThreadTest
    fun testGenerateProblems() {
        val activity = FlashCardActivity()
        val problems = activity.generateProblems()

        // Check that 10 problems were generated
        assertEquals(10, problems.size)

        // Check that each problem has a valid answer
        for (problem in problems) {
            val expectedAnswer = if (problem.operator == "+") problem.operand1 + problem.operand2 else problem.operand1 - problem.operand2
            assertEquals(expectedAnswer, problem.answer)
        }

        // Check that operands are within expected range
        for (problem in problems) {
            assertTrue(problem.operand1 in 1..100)
            assertTrue(problem.operand2 in 1..21)
        }

        // Check that operator is "+" or "-"
        for (problem in problems) {
            assertTrue(problem.operator == "+" || problem.operator == "-")
        }
    }

    @Test
    fun testProblemAnswer() {
        val problem = FlashCardActivity.Problem(4, 2, "+")
        assertEquals(6, problem.answer)

        val problem2 = FlashCardActivity.Problem(4, 2, "-")
        assertEquals(2, problem2.answer)
    }
}




