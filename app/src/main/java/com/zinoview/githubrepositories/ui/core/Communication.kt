package com.zinoview.githubrepositories.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.IllegalStateException


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */


interface Communication<T> {

    fun observe(owner: LifecycleOwner, observer: Observer<T>)
    fun changeValue(value: T)

    abstract class Base<T : CommunicationModel> : Communication<List<T>> {
        private val liveData = MutableLiveData<List<T>>()

        override fun observe(owner: LifecycleOwner, observer: Observer<List<T>>) {
            liveData.observe(owner,observer)
        }

        override fun changeValue(value: List<T>) {
            liveData.value = value
        }
    }

    interface TestCommunication<T> : Communication<T> {

        fun lastValue() : T

        fun observe()

        fun countObserve() : Int

        class Test : TestCommunication<String> {

            interface TestLiveData<T> {

                fun observe()

                fun value() : T

                fun value(value: T)

                class Base<T>(defaultValue: T?) : TestLiveData<T> {

                    private var lastValue: T? = defaultValue

                    override fun observe() = Unit

                    override fun value(): T = lastValue!!

                    override fun value(value: T) {
                        lastValue = value
                    }
                }
            }



            private var countObserve = 0

            private val liveData = TestLiveData.Base<String>("Default value")

            override fun observe(owner: LifecycleOwner, observer: Observer<String>)
                = throw IllegalStateException("Communication.TestCommunication not use observe()")

            override fun observe() {
                countObserve++
            }

            override fun changeValue(value: String) {
                liveData.value(value)
            }

            override fun lastValue(): String  {
                return liveData.value()
            }

            override fun countObserve(): Int
                = countObserve
        }
    }

}