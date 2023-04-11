package com.example.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.remote.retrofit.ApiConfig
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.viewmodel.ListItem
import com.example.githubuser.viewmodel.ListUserViewmodel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingFragment() : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val listUserViewmodel by viewModels<ListUserViewmodel>()
    private var username: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        username = arguments?.getString("username")
        getFollowing(username)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listUserViewmodel.listUser.observe(viewLifecycleOwner) { listUser ->
            if (listUser != null) {
                setUserData(listUser)
            }
        }
        listUserViewmodel.isLoadingUsers.observe(viewLifecycleOwner) { loading ->
            showLoading(
                loading
            )
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getFollowing (username: String?){
        if (username != null) {
            listUserViewmodel.getUsers(username, ListItem.FOLLOWING)
        }
    }

    private fun setUserData(users: List<ItemsItem>){
        val listUser = ArrayList<ItemsItem>()
        for (user in users){
            listUser.add(user)
        }
        val adapter = UserAdapter(requireActivity(), listUser)
        binding.rvFollowing.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(HomeFragment.EXTRA_USERNAME, data.login)
                startActivity(intent)
            }
        })

    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(username: String?): FollowingFragment {
            val args = Bundle().apply {
                putString("username", username)
            }
            return FollowingFragment().apply {
                arguments = args
            }
        }
    }

}