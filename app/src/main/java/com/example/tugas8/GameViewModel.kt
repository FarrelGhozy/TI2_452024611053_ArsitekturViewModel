package com.example.tugas8

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val currentScrambledWord: String = "",
    val score: Int = 0,
    val wordCount: Int = 1,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private lateinit var currentWord: String
    private var usedWords = mutableSetOf<String>()

    private val wordsList = listOf(
        "android", "kotlin", "jetpack", "compose", "viewmodel",
        "activity", "fragment", "service", "broadcast", "provider",
        "intent", "layout", "resource", "manifest", "gradle",
        "debug", "release", "build", "compile", "library"
    )

    init {
        pickNewWord()
    }

    fun submitGuess(guess: String) {
        if (guess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score + 1
            val updatedWordCount = _uiState.value.wordCount + 1

            if (updatedWordCount > MAX_NO_OF_WORDS) {
                _uiState.update {
                    it.copy(
                        score = updatedScore,
                        isGuessedWordWrong = false,
                        isGameOver = true
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        score = updatedScore,
                        wordCount = updatedWordCount,
                        isGuessedWordWrong = false
                    )
                }
                pickNewWord()
            }
        } else {
            _uiState.update { it.copy(isGuessedWordWrong = true) }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(isGuessedWordWrong = false) }
    }

    fun skipWord() {
        val updatedWordCount = _uiState.value.wordCount + 1

        if (updatedWordCount > MAX_NO_OF_WORDS) {
            _uiState.update { it.copy(isGameOver = true) }
        } else {
            _uiState.update {
                it.copy(
                    wordCount = updatedWordCount,
                    isGuessedWordWrong = false
                )
            }
            pickNewWord()
        }
    }

    fun restartGame() {
        usedWords.clear()
        _uiState.update {
            UiState()
        }
        pickNewWord()
    }

    private fun pickNewWord() {
        val availableWords = wordsList.filter { it !in usedWords }
        currentWord = if (availableWords.isNotEmpty()) {
            availableWords.random()
        } else {
            usedWords.clear()
            wordsList.random()
        }
        usedWords.add(currentWord)
        val scrambled = scrambleWord(currentWord)
        _uiState.update { it.copy(currentScrambledWord = scrambled, isGuessedWordWrong = false) }
    }

    private fun scrambleWord(word: String): String {
        val chars = word.toCharArray()
        do {
            chars.shuffle()
        } while (String(chars) == word)
        return String(chars)
    }

    companion object {
        const val MAX_NO_OF_WORDS = 10
    }
}
