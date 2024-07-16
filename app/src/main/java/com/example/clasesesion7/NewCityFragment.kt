package com.example.clasesesion7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.clasesesion7.databinding.FragmentNewCityBinding
import kotlinx.coroutines.launch
import online.jadg13.clase7.dao.DatabaseBuilder
import online.jadg13.clase7.entities.City


class NewCityFragment : Fragment() {
    private lateinit var binding: FragmentNewCityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewCityBinding.inflate(layoutInflater)
        startCity()
        return binding.root
    }

    private fun startCity() {
        binding.btnSave.setOnClickListener {
            saveCity()
        }
    }

    private fun saveCity() {
        val context = requireContext()
        val db = DatabaseBuilder.getInstance(context)
        val cityDao = db.cityDao()
        val name = binding.tfName.editText?.text.toString()
        val description = binding.tfDescription.editText?.text.toString()
        val population = binding.tfPopulation.editText?.text.toString().toInt()
        val city  = City(0, name, description, population)
        viewLifecycleOwner.lifecycleScope.launch {
            cityDao.insert(city)
        }
        binding.tfName.editText?.setText("")
        binding.tfDescription.editText?.setText("")
        binding.tfPopulation.editText?.setText("")
        binding.tfName.requestFocus()
        findNavController().popBackStack()
    }
}