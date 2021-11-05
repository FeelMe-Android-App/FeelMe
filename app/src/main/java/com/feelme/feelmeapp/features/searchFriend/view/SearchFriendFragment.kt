package com.feelme.feelmeapp.features.searchFriend.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.feelme.feelmeapp.R
import com.feelme.feelmeapp.adapters.PagingFriendsAdapter.PagedFriendsAdapter
import com.feelme.feelmeapp.databinding.FragmentSearchFriendBinding
import com.feelme.feelmeapp.features.searchFriend.viewmodel.SearchFriendViewModel
import com.feelme.feelmeapp.features.userProfile.view.UserProfileActivity
import com.feelme.feelmeapp.utils.Command
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.timerTask

class SearchFriendFragment : Fragment() {
    private var binding: FragmentSearchFriendBinding? = null
    private lateinit var timer: Timer
    private val viewModel: SearchFriendViewModel by viewModel()
    private val pagedFriendsAdapter: PagedFriendsAdapter by lazy {
        PagedFriendsAdapter { friend ->
            val intent = Intent(context, UserProfileActivity::class.java)
            intent.putExtra(USER_ID, friend.userId)
            startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchFriendBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = Timer()
        viewModel.command = MutableLiveData()
        hideLoader()

        binding?.let {
            it.btBack.setOnClickListener {
                findNavController().navigate(R.id.action_searchFriendFragment_to_feedFragment)
            }

            it.rvFriendsSearch.layoutManager = LinearLayoutManager(context)

            it.tiSearchFriends.requestFocus()

            it.tiSearchFriends.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard(activity = context as Activity)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText.isNullOrEmpty()) {
                        hideLoader()
                    } else {
                        showLoader()
                        pagedFriendsAdapter.submitData(lifecycle, PagingData.empty())
                        timer.cancel()
                        timer = Timer()
                        timer.schedule(timerTask {
                            viewModel.recyclerViewReloaded()
                            lifecycleScope.launch {
                                val initialLoad = viewModel.onSuccessFriendSearch.value == "refreshed"
                                viewModel.getFriendSearch(newText, initialLoad).collectLatest { pagingData ->
                                    pagedFriendsAdapter.submitData(pagingData)
                                }
                            }
                        }, 1000)
                    }
                    return true
                }
            })
        }
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.onSuccessFriendSearch.observe(viewLifecycleOwner, {
            binding?.let { bind ->
                bind.rvFriendsSearch.layoutManager = LinearLayoutManager(context)
                bind.rvFriendsSearch.adapter = pagedFriendsAdapter
                bind.rvFriendsSearch.isVisible = it != "refreshed"
                showFriendsResult()
            }
        })
    }

    fun hideLoader() {
        binding?.let {
            it.vgLoader.vgLoader.isVisible = false
            it.rvFriendsSearch.isVisible = false
        }
    }

    fun showLoader() {
        binding?.let {
            it.rvFriendsSearch.isVisible = false
            it.vgLoader.vgLoader.isVisible = true
        }
    }

    fun showFriendsResult() {
        binding?.let {
            it.rvFriendsSearch.isVisible = true
            it.vgLoader.vgLoader.isVisible = false
        }
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = activity.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            currentFocusedView.clearFocus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val USER_ID = "userId"
    }
}
