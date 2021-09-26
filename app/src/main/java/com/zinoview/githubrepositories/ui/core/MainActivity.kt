package com.zinoview.githubrepositories.ui.core

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.GAApp
import com.zinoview.githubrepositories.core.Save
import com.zinoview.githubrepositories.ui.repositories.download.DialogWrapper
import com.zinoview.githubrepositories.ui.repositories.download.SnackBarWrapper
import com.zinoview.githubrepositories.ui.repositories.download.ViewWrapper
import com.zinoview.githubrepositories.ui.repositories.download.WriteFileViewModel
import com.zinoview.githubrepositories.ui.users.fragment.GithubUsersFragment
import okhttp3.ResponseBody


class MainActivity : AppCompatActivity(), Save<ResponseBody> {

    val toolbar by lazy {
       supportActionBar
    }

    private val gApplication by lazy {
        application as GAApp
    }

    private val writeFileViewModel by lazy {
        gApplication.viewModel(WriteFileViewModel.Base::class.java,this)
    }

    private lateinit var mainView: ConstraintLayout
    private lateinit var viewWrapper: ViewWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,GithubUsersFragment())
            .commit()

        mainView = findViewById<ConstraintLayout>(R.id.main_view)

        viewWrapper = ViewWrapper.Base(
            SnackBarWrapper.Base(mainView),
            DialogWrapper.Base(dialog())
        )

    }

    override fun onStart() {
        super.onStart()

        writeFileViewModel.observe(this) { results ->
            results.first().handleResult(viewWrapper)
        }
    }

    override fun onBackPressed() {
        val baseFragments = supportFragmentManager.fragments.map { it as BaseFragment }
        if(baseFragments[0].onBackPressed()) super.onBackPressed()
    }

    override fun saveData(data: ResponseBody) {
        viewWrapper.showDialog()
        writeFileViewModel.writeData(data)
    }

    private fun dialog(): Dialog {
        return Dialog(this).apply {
            setCancelable(false)
            setContentView(R.layout.downloading_file_layout)
        }
    }

}
