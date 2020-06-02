package com.milanpurbia.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.milanpurbia.mvvmnewsapp.R
import com.milanpurbia.mvvmnewsapp.ui.NewsActivity
import com.milanpurbia.mvvmnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {
lateinit var viewmodel: NewsViewModel
    val args:ArticleFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel=(activity as NewsActivity).viewmodel
    val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)

        }
        fab.setOnClickListener {
            viewmodel.saveArticles(article)
            Snackbar.make(view,"article saved successfully",Snackbar.LENGTH_SHORT).show()
        }
    }
}