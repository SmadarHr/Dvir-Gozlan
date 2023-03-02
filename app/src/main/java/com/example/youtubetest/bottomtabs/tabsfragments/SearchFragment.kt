package com.example.youtubetest.bottomtabs.tabsfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubetest.adapters.VideoAdapter
import com.example.youtubetest.databinding.FragmentSearchBinding
import com.example.youtubetest.models.SearchResultItem
import com.example.youtubetest.models.Video
import com.example.youtubetest.viewmodels.SearchViewModel

class SearchFragment : Fragment() {


    private lateinit var videosRecyclerView: RecyclerView
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel = SearchViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        initView()

        return binding.root
    }

    private fun initView() {
        videosRecyclerView = binding.recyclerView
        videosRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        videosRecyclerView.setHasFixedSize(true)
        binding.searchView.setOnQueryTextListener(onSearchQueryTextListener())
        searchViewModel.getLiveData().observe(viewLifecycleOwner, searchObserver)
    }

    private fun onSearchQueryTextListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchViewModel.performSearch(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
    }

    private val searchObserver = Observer<List<SearchResultItem>> { results ->
        val videoTitle = arrayListOf<String>()
        val imageUrl = arrayListOf<String>()
        val videoId = arrayListOf<String>()
        for (video: SearchResultItem in results) {
            videoTitle.add(video.snippet.title)
            imageUrl.add(video.snippet.thumbnails.default.url)
            videoId.add(video.id.videoId)
        }
        fetchItems(videoTitle, imageUrl, videoId)
    }

    private fun fetchItems(
        videoTitle: ArrayList<String>,
        imageUrl: ArrayList<String>,
        videoId: ArrayList<String>
    ) {
        val videosArray: ArrayList<Video> = arrayListOf<Video>()
        val videosImages = ArrayList<ImageView>()
        for (i in videoTitle.indices) {
            videosImages.add(ImageView(context))
            Glide.with(this)
                .load(imageUrl[i])
                .into(videosImages[i])
            val video = Video(videoTitle[i], videosImages[i], videoId[i])
            videosArray.add(video)
        }
        videosRecyclerView.adapter = VideoAdapter(videosArray)
    }
}