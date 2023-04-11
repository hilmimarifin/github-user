package com.example.githubuser.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.*
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.ListUserResponse
import com.example.githubuser.data.remote.retrofit.ApiConfig
import com.example.githubuser.databinding.FragmentHomeBinding
import com.example.githubuser.viewmodel.ListItem
import com.example.githubuser.viewmodel.ListUserViewmodel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var usernameFromSearch: String? = null
    private val listUserViewmodel by viewModels<ListUserViewmodel>()
    companion object {
        val EXTRA_USERNAME = "extra_name"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        usernameFromSearch = arguments?.getString(MainActivity.EXTRA_USERNAME)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (usernameFromSearch != null) {
            listUserViewmodel.getUsers(usernameFromSearch!!, ListItem.USER)
        }

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
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUserData(users: List<ItemsItem>){
        val listUser = ArrayList<ItemsItem>()
        for (user in users){
            listUser.add(user)
        }
        val adapter = UserAdapter(requireActivity(), listUser)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val username = data.login
                val mBundle = Bundle()
                mBundle.putString(EXTRA_USERNAME, username)
                val toDetailUser = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
                view?.findNavController()?.navigate(R.id.action_homeFragment_to_detailActivity, mBundle)
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

}



