package com.example.yeebum.screens.bulbs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.databases.bulbs_database.BulbsViewModel
import com.example.yeebum.databases.bulbs_database.BulbsViewModelFactory
import com.example.yeebum.models.Bulb
import com.example.yeebum.screens.adapters.recycler_views.AllBulbsRecyclerViewAdapter
import com.example.yeebum.screens.components.AppDrawer
import com.example.yeebum.screens.components.Helpers
import kotlinx.android.synthetic.main.fragment_all_bulbs.*


class AllBulbsFragment : Fragment(), BulbsInterface {

    private val helpers = Helpers()
    private val bulbsViewModel: BulbsViewModel by viewModels {
        BulbsViewModelFactory((requireActivity().application as YeebumApplication).bulbsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_all_bulbs, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDrawer()
        getBulbs()

        addBulbButton.setOnClickListener {
            findNavController().navigate(AllBulbsFragmentDirections.actionAllBulbsFragmentToEnterBulbDataFragment())
        }


        homeSettingsButton.setOnClickListener {
            findNavController().navigate(
                AllBulbsFragmentDirections.actionAllBulbsFragmentToSettingsFragment()
            )
        }

    }

    override fun onBulbClick(ip: String, port: Int) {
        val bundle = bundleOf("ip" to ip, "port" to port)
        findNavController().navigate(
            AllBulbsFragmentDirections.actionAllBulbsFragmentToControlFragment().actionId,
            bundle
        )
    }


    //------------------------| Get Bulbs From database and setup it into recyclerview |-------------------------
    private fun getBulbs() {
        bulbsViewModel.allBulbs.observe(viewLifecycleOwner) {
            bulbsRecyclerView?.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = AllBulbsRecyclerViewAdapter(it, this@AllBulbsFragment)
            }
        }
    }
    //============================================================================================================


    private fun setupDrawer() {
        val drawer: AppDrawer = AppDrawer()
        drawer.setOpenDrawer(allBulbsDrawerButton, requireActivity())
    }


    override fun onLongClickBulb(bulb: Bulb) {
        helpers.getChooseOptionDialogBulb(
            requireActivity(),
            requireContext(),
            "What you want to do?",
            this,
            bulb
        ).show()
    }

    override fun onDeleteBulb(bulb: Bulb) {
        bulbsViewModel.deleteBulb(bulb)
        helpers.showSnackBar(requireView(),"Deleted","Undo"){
            bulbsViewModel.insertBulb(bulb)
        }
    }

    override fun onEditBulb(bulb: Bulb) {

    }


}