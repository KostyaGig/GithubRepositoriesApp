package com.zinoview.githubrepositories.ui.users

import androidx.appcompat.widget.SearchView
import com.zinoview.githubrepositories.ui.message
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubObservableQuery {

    fun query(searchView: SearchView, query: (String) -> Unit)

    //todo use GithubUserDisposable for cleared

    class Base : GithubObservableQuery {

        override fun query(searchView: SearchView, query: (String) -> Unit) {

            val observableQuery = io.reactivex.Observable.create(ObservableOnSubscribe<String> { subscriber->
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        subscriber.onNext(query!!)
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        subscriber.onNext(newText!!)
                        return false
                    }
                })
            })

            observableQuery
                .subscribeOn(Schedulers.io())
                .map{query->
                    query.lowercase().trim()
                }
                .debounce(350, TimeUnit.MILLISECONDS)
                .filter { query -> query.isNotBlank() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { query->
                        message(query)
                        query(query)
                    },
                    {
                        message("onError ${it.message}")
                    }
                )
        }
    }
}