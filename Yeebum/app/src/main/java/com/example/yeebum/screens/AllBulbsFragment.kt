package com.example.yeebum.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.databases.bulbs_database.BulbsViewModel
import com.example.yeebum.databases.bulbs_database.BulbsViewModelFactory
import com.example.yeebum.screens.adapters.recycler_views.AllBulbsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_all_bulbs.*


class AllBulbsFragment : Fragment(), BulbsInterface {

    private val bulbsViewModel: BulbsViewModel by viewModels{
        BulbsViewModelFactory((requireActivity().application as YeebumApplication).bulbsRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_all_bulbs, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupDrawer()
        getBulbs()

        addBulbButton.setOnClickListener {
            findNavController().navigate(AllBulbsFragmentDirections.actionAllBulbsFragmentToEnterBulbDataFragment())
        }


    }

    override fun onBulbClick() {
        findNavController().navigate(AllBulbsFragmentDirections.actionAllBulbsFragmentToControlFragment())
    }

    //------------------------| Get Bulbs From database and setup it into recyclerview |-------------------------
    private fun getBulbs(){
        bulbsViewModel.allBulbs.observe(viewLifecycleOwner){
            bulbsRecyclerView?.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = AllBulbsRecyclerViewAdapter(it,this@AllBulbsFragment)
            }
        }
    }
    //============================================================================================================


    private fun setupDrawer(){
        val drawer: AppDrawer = AppDrawer()
        drawer.setOpenDrawer(allBulbsDrawerButton,requireActivity())
    }

}