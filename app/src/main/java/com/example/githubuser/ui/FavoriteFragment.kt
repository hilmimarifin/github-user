package com.example.githubuser.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.FragmentFavoriteBinding
import com.example.githubuser.viewmodel.DetailViewmodel
import com.example.githubuser.viewmodel.FavoriteViewmodel
import com.example.githubuser.viewmodel.ViewmodelFactory


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
        (activity as AppCompatActivity).supportActionBar?.title = "Favorite User"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favViewmodel = obtainViewModel(this)
        favViewmodel.getAllFavoriteUser().observe(viewLifecycleOwner){ listUser ->
            if (listUser != null) {
                setUserData(listUser)
            }
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    private fun setUserData(users: List<FavoriteUser>){
        val listUser = ArrayList<FavoriteUser>()
        for (user in users){
            listUser.add(user)
        }
        val adapter = FavoriteAdapter(requireActivity(), listUser)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteUser) {
                val username = data.username
                val mBundle = Bundle()
                mBundle.putString(HomeFragment.EXTRA_USERNAME, username)
                val toDetailUser = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
                view?.findNavController()?.navigate(R.id.action_favoriteFragment_to_detailActivity, mBundle)
            }
        })
    }

    private fun obtainViewModel(fragment: Fragment): FavoriteViewmodel {
        val factory = ViewmodelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[FavoriteViewmodel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Show the action bar
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.app_name)
    }

}