package com.milanpurbia.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.milanpurbia.mvvmnewsapp.Adapters.NewsAdapter
import com.milanpurbia.mvvmnewsapp.R
import com.milanpurbia.mvvmnewsapp.ui.NewsActivity
import com.milanpurbia.mvvmnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    lateinit var viewmodel:NewsViewModel
    lateinit var newsAdapter:NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel=(activity as NewsActivity).viewmodel
        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle =Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
        val itemTouchhelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
          val position =viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewmodel.deleteArticle(article)
                Snackbar.make(view,"Succesfully deleted article",Snackbar.LENGTH_LONG).apply {
                    setAction("undo"){
                        viewmodel.saveArticles(article)
                    }
                    show()
                }
            }
        }
ItemTouchHelper(itemTouchhelperCallback).apply {
    attachToRecyclerView(rvSavedNews)
}
viewmodel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
    newsAdapter.differ.submitList(articles)

})

    }
    private fun setupRecyclerView()
    {
        newsAdapter= NewsAdapter()
        rvSavedNews.apply {
            adapter =newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }

    }
}