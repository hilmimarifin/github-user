package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
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
                mBundle.putString(HomeFragment.EXTRA_USERNAME, username)
                val toDetailUser = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
                view?.findNavController()?.navigate(R.id.action_homeFragment_to_detailActivity, mBundle)
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}