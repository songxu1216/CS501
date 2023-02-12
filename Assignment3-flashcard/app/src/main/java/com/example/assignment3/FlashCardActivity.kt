package com.example.assignment3

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import kotlin.random.Random

class FlashCardActivity : AppCompatActivity() {
    private lateinit var generateButton: Button
    private lateinit var submitButton: Button
    private lateinit var problemTextView: TextView
    private lateinit var answerEditText: EditText

    private var problems = mutableListOf<Problem>()
    private var currentProblemIndex = 0
    private var score = 0
    private var isGenerated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)

        val username = intent.getStringExtra("username")

        generateButton = findViewById(R.id.generateProblemsButton)
        submitButton = findViewById(R.id.submitAnswerButton)
        problemTextView = findViewById(R.id.problemTextView)
        answerEditText = findViewById(R.id.answerEditText)

        generateButton.setOnClickListener {
            if (!isGenerated) {
                problems = generateProblems()
                currentProblemIndex = 0
                score = 0
                isGenerated = true
                generateButton.isEnabled = false
                submitButton.isEnabled = true
            }
            showProblem()
        }

        submitButton.setOnClickListener {
            val currentProblem = problems[currentProblemIndex]
            val userAnswer = answerEditText.text.toString()

            if (userAnswer == currentProblem.answer.toString()) {
                score++
            }

            currentProblemIndex++

            if (currentProblemIndex < problems.size) {
                showProblem()
            } else {
                isGenerated = false
                generateButton.isEnabled = true
                submitButton.isEnabled = false
                Toast.makeText(this, "Score: $score out of ${problems.size}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateProblems(): MutableList<Problem> {
        val problems = mutableListOf<Problem>()

        for (i in 1..10) {
            val operator = if (Random.nextBoolean()) "+" else "-"
            val operand1 = Random.nextInt(1, 100)
            val operand2 = Random.nextInt(1, 21)

            val problem = Problem(operand1, operand2, operator)
            problems.add(problem)
        }

        return problems
    }

    private fun showProblem() {
        val currentProblem = problems[currentProblemIndex]
        val problemText = "${currentProblem.operand1} ${currentProblem.operator} ${currentProblem.operand2} = "
        problemTextView.text = problemText
        answerEditText.text.clear()
    }

    data class Problem(val operand1: Int, val operand2: Int, val operator: String) {
        val answer: Int
            get() = if (operator == "+") operand1 + operand2 else operand1 - operand2
    }
}

