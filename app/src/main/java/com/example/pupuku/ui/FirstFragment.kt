package com.example.pupuku.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pupuku.R
import com.example.pupuku.application.FertilizerApplication
import com.example.pupuku.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val fertilizerViewModel: FertilizerViewModel by viewModels {
        FertilizerViewModelFactory((applicationContext as FertilizerApplication).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FertilizerListAdapter { fertilizer ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(fertilizer)
            findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        fertilizerViewModel.allFertilizer.observe(viewLifecycleOwner){ fertilizers ->
            fertilizers.let {
            if (fertilizers.isEmpty()) {
                binding.imageView.visibility = View.VISIBLE
            } else {
                binding.imageView.visibility = View.GONE
            }
                adapter.submitList(fertilizers)
            }

        }

        binding.addFAB.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}