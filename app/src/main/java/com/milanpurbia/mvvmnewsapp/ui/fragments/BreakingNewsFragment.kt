package com.milanpurbia.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.milanpurbia.mvvmnewsapp.Adapters.NewsAdapter
import com.milanpurbia.mvvmnewsapp.R
import com.milanpurbia.mvvmnewsapp.ui.NewsActivity
import com.milanpurbia.mvvmnewsapp.ui.NewsViewModel
import com.milanpurbia.mvvmnewsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewmodel:NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG= "BreakingNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel=(activity as NewsActivity).viewmodel
        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle =Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        viewmodel.breakingNews.observe(viewLifecycleOwner, Observer {   response->
            when(response){
                is Resource.Success ->
                {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.message?.let{
                        message ->
                        Log.e(TAG,"an error occured: $message")
                    }
                }
                is Resource.Loading ->
                {
                    showingProgressBar()
                }
            }
        })

    }
    private fun hideProgressBar()
    {
        paginationProgressBar.visibility =View.INVISIBLE
    }
    private fun showingProgressBar()
    {
        paginationProgressBar.visibility =View.VISIBLE
    }
    private fun setupRecyclerView()
    {
        newsAdapter= NewsAdapter()
        rvBreakingNews.apply {
        adapter =newsAdapter
            layoutManager=LinearLayoutManager(activity)
        }

    }
}