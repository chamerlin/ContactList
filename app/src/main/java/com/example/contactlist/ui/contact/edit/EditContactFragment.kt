package com.example.contactlist.ui.contact.edit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.contactlist.ui.contact.base.BaseContactFragment
import com.example.contactlist.ui.contact.edit.viewModel.EditContactViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditContactFragment : BaseContactFragment() {
    private val viewModel: EditContactViewModelImpl by viewModels()
    val args: EditContactFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.onViewCreated(args.id)
        onBindView()
    }

    fun onBindView() {
        binding.btnSave.setOnClickListener{
            viewModel.update(args.id)
        }

        viewModel.error.asLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.finish.asLiveData().observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult("edit_contact_finished", bundle)
            NavHostFragment.findNavController(this).popBackStack()
        }
    }
}