package com.example.pupuku.ui

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pupuku.R
import com.example.pupuku.application.FertilizerApplication
import com.example.pupuku.databinding.FragmentSecondBinding
import com.example.pupuku.model.Fertilizer

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val fertilizerViewModel: FertilizerViewModel by viewModels {
        FertilizerViewModelFactory((applicationContext as FertilizerApplication).repository)
    }
    private val args: SecondFragmentArgs by navArgs()
    private var fertilizer: Fertilizer? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fertilizer = args.fertilizer
        // kita cek jika fertilizer null maka tampilan deflaut nambah  distributor pupuk
        // jika fertilizer tidak nul tampilan sedikit berubah ada tombol hapus dan pupuk
        if (fertilizer != null) {
            binding.SaveButton.text = "Ubah"
            binding.nameEditText.setText(fertilizer?.name)
            binding.addressEditText.setText(fertilizer?.address)
            binding.typeEditText.setText(fertilizer?.type)
        }
        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val type = binding.typeEditText.text
        binding.SaveButton.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()) {
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (type.isEmpty()) {
                Toast.makeText(context, "Jenis Pupuk tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (fertilizer == null) {
                    val fertilizer =
                        Fertilizer(0, name.toString(), address.toString(), type.toString())
                    fertilizerViewModel.insert(fertilizer)
                } else {
                    val fertilizer = Fertilizer(
                        fertilizer?.id!!,
                        name.toString(),
                        address.toString(),
                        type.toString()
                    )
                    fertilizerViewModel.update(fertilizer)
                }
                findNavController().popBackStack() // untuk dismiss halaman ini
            }
            }

            binding.DeleteButton.setOnClickListener {
                fertilizer?.let { fertilizerViewModel.delete(it) }
                findNavController().popBackStack()
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }

