package com.example.assignment3

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random


class FlashCardActivity : AppCompatActivity() {
    lateinit var generateButton: Button
    lateinit var submitButton: Button
    private lateinit var problemTextView: TextView
    private lateinit var answerEditText: EditText

    private var problems = arrayListOf<Problem>()
    private var currentProblemIndex = 0
    private var score = 0
    private var isGenerated = false

    companion object {
        const val KEY_CURRENT_PROBLEM_INDEX = "current_problem_index"
        const val KEY_SCORE = "score"
        const val KEY_IS_GENERATED = "is_generated"
        const val KEY_PROBLEMS = "problems"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)

        val username = intent.getStringExtra("username")
        Toast.makeText(this, "Welcome $username", Toast.LENGTH_SHORT).show()

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

        if (savedInstanceState != null) {
            currentProblemIndex = savedInstanceState.getInt(KEY_CURRENT_PROBLEM_INDEX)
            score = savedInstanceState.getInt(KEY_SCORE)
            isGenerated = savedInstanceState.getBoolean(KEY_IS_GENERATED)
            problems = savedInstanceState.getParcelableArrayList<Problem>(KEY_PROBLEMS) ?: arrayListOf()


            if (isGenerated) {
                generateButton.isEnabled = false
                submitButton.isEnabled = true
                showProblem()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_PROBLEM_INDEX, currentProblemIndex)
        outState.putInt(KEY_SCORE, score)
        outState.putBoolean(KEY_IS_GENERATED, isGenerated)
        outState.putParcelableArrayList(KEY_PROBLEMS, ArrayList(problems))
    }

    fun generateProblems(): ArrayList<Problem> {
        val problems = ArrayList<Problem>()

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

    data class Problem(val operand1: Int, val operand2: Int, val operator: String) : Parcelable {
        val answer: Int
            get() = if (operator == "+") operand1 + operand2 else operand1 - operand2

        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString()!!
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(operand1)
            parcel.writeInt(operand2)
            parcel.writeString(operator)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Problem> {
            override fun createFromParcel(parcel: Parcel): Problem {
                return Problem(parcel)
            }

            override fun newArray(size: Int): Array<Problem?> {
                return arrayOfNulls(size)
            }
        }
    }

}

