package com.zinoview.githubrepositories.data.repositories.download.file

import kotlin.math.roundToInt

interface PercentageOfNumber {

    fun take(number1: Int,number2: Int) : Int

    class Base : PercentageOfNumber {

        /**
         *
         * Example:
         * number1: 10
         * number2 5
         * Percentage number2 of number1 = number2/number1 * 100
         * 5/10 * 100 = 50%
         **/

        override fun take(number1: Int, number2: Int): Int
            = (number2 / number1.toFloat() * 100).roundToInt()
    }

    class Test : PercentageOfNumber {

        override fun take(number1: Int, number2: Int): Int {
            return (number2 / number1.toFloat() * 100).roundToInt()
        }
    }
}