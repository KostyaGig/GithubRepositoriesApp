package com.zinoview.githubrepositories.ui.users

import androidx.appcompat.widget.SearchView
import com.zinoview.githubrepositories.ui.message
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubObservableQuery {

    fun query(
        searchView: SearchView,
        query: (String) -> Unit,
        emptyQuery: () -> Unit
    )

    class Base(
        private val githubUserDisposableStore: GithubDisposableStore
    ) : GithubObservableQuery, CleanDisposable {

        override fun query(
            searchView: SearchView,
            stringQuery: (String) -> Unit,
            emptyQuery: () -> Unit
        ) {

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
                .map{ query->
                    query.lowercase().trim()
                }
                .debounce(DELAY_QUERY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { query->
                        if (query.isEmpty()) {
                            emptyQuery()
                        } else {
                            stringQuery(query)
                        }
                    },
                    {
                        message("GithubObservableQuery onError ${it.message}")
                        throw it
                    }
                ).addToDisposableStore(githubUserDisposableStore)
        }

        override fun Disposable.addToDisposableStore(store: GithubDisposableStore)
            = store.add(this)

        private companion object {
            const val DELAY_QUERY = 400L
        }
    }

}