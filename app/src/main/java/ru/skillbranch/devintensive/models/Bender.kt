package ru.skillbranch.devintensive.models

import java.util.*

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun asqQuestion(): String = when(question){
                Question.NAME -> Question.NAME.question
                Question.PROFESSION -> Question.PROFESSION.question
                Question.MATERIAL -> Question.MATERIAL.question
                Question.BDAY -> Question.BDAY.question
                Question.SERIAL -> Question.SERIAL.question
                Question.IDLE -> Question.IDLE.question
    }


    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>>{

        var tooMuchErrorsText = ""
        var verdict = ""
        val (isValid, notValidateText) = question.validateAnswer(answer)

        if (!isValid) {
            verdict = notValidateText
        }
        else if(question.answers.contains(answer.toLowerCase(Locale.getDefault()))){
            question = question.nextQuestion()
            verdict = "Отлично - ты справился"
        } else {
            if (question != Question.IDLE) {
                status = status.nextStatus()
                verdict = "Это не правильный ответ"
                if (status == Status.NORMAL) {
                    tooMuchErrorsText = ". Давай все по новой"
                    question = Question.NAME
                }
            }
        }
        return "${verdict}${tooMuchErrorsText}\n${question.question}" to status.color
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return (answer.capitalize() == answer) to "Имя должно начинаться с заглавной буквы"
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return (answer.toLowerCase() == answer) to "Профессия должна начинаться со " +
                        "строчной буквы"
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return (answer.replace(Regex("[0-9]"), "") == answer) to
                        "Материал не должен содержать цифр"
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return (answer.replace(Regex("[aA-zZ]|[аА-яЯ]"), "") == answer) to
                        "Год моего рождения должен содержать только цифры"
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return (answer.replace(Regex("[aA-zZ]|[аА-яЯ]"), "") == answer
                        && answer.length == 7) to "Серийный номер содержит только цифры, и их 7"
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return true to ""
            }
        };

        abstract fun nextQuestion(): Question

        abstract fun validateAnswer(answer: String): Pair<Boolean, String>

    }

}