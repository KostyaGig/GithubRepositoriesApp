package com.zinoview.githubrepositories.data.repositories.download.file


/**
 * Transform size file from Bytes to Kb
 * */

interface Kbs {

    fun toKb(dataInBytes: Int) : Int

    fun toKb(dataInBytes: Long) : Int

    class Base : Kbs {

        override fun toKb(dataInBytes: Long): Int
            = (dataInBytes / DIVISOR).toInt()

        override fun toKb(dataInBytes: Int): Int
            = toKb(dataInBytes.toLong())

        private companion object {
            const val DIVISOR = 1024
        }
    }

    class Test : Kbs {

        override fun toKb(dataInBytes: Long): Int
                = (dataInBytes / DIVISOR).toInt()

        override fun toKb(dataInBytes: Int): Int
                = toKb(dataInBytes.toLong())

        private companion object {
            const val DIVISOR = 1024
        }

    }
}