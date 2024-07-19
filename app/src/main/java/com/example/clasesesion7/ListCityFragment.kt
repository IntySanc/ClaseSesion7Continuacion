package com.example.clasesesion7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.clasesesion7.adapter.CityAdapter
import com.example.clasesesion7.adapter.CityViewModel
import com.example.clasesesion7.adapter.CityViewModelFactory
import com.example.clasesesion7.databinding.FragmentListCityBinding
import online.jadg13.clase7.dao.DatabaseBuilder
import online.jadg13.clase7.entities.City


class ListCityFragment : Fragment() {
    private lateinit var binding: FragmentListCityBinding

    private val cityViewModel : CityViewModel by viewModels {
        CityViewModelFactory(DatabaseBuilder.getInstance(requireContext()).cityDao())
    }

    private lateinit var cityListAdapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListCityBinding.inflate(layoutInflater)
        startList()
        return binding.root
    }

    private fun startList() {

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.go_NewCityFragment)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityListAdapter = CityAdapter(requireContext(), emptyList(), this::onEditclick, this::onDeleteclick)
        binding.lvRegistros.adapter = cityListAdapter

        cityViewModel.allCities.observe(viewLifecycleOwner, Observer{
                cities -> cityListAdapter.updateCities(cities)
        } )

    }

    private fun onEditclick(city: City){
        val bundle= Bundle()
        bundle.putInt("city_id", city.id)
        findNavController().navigate(R.id.go_EditCityFragment, bundle)

    }

    private fun onDeleteclick(city: City){
        cityViewModel.deleteCity(city)
        Toast.makeText(context,"Registro Eliminado", Toast.LENGTH_LONG).show()

    }

}