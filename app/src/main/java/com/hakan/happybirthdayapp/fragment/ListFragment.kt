package com.hakan.happybirthdayapp.fragment

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hakan.happybirthdayapp.R
import com.hakan.happybirthdayapp.adapter.RecyclerViewAdapter
import com.hakan.happybirthdayapp.data.User
import com.hakan.happybirthdayapp.data.UserViewModel
import com.hakan.happybirthdayapp.databinding.FragmentListBinding
import com.hakan.happybirthdayapp.swipe.ItemSwipe


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding get() = _binding!!

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    /**
     * ing. acıklama
     * @author kim
     * @since 24.08.2022
     * */
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupSearch()
        setupRecyclerview()
        handleClickEvents()
        subscribeObservers()

        userViewModel.readAllData()
    }


    private fun setupSearch() {
        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.etSearch.text.toString().length >= 2) {
                    handleSearch(binding.etSearch.text.toString())
                }
                return@OnEditorActionListener true
            }
            false
        })
    }



    private fun handleSearch(text: String) {
        userViewModel.readAllData(text)
    }

    /**
     *
     * @author Hakan Akbudak
     * @since 02.09.2022
     * */
    private fun subscribeObservers() {
        userViewModel.allUserData.observe(viewLifecycleOwner) { userList ->
            if (userList.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Liste boş", Toast.LENGTH_SHORT).show()
            }
            recyclerViewAdapter.setData(userList)
        }
    }

    private fun setupRecyclerview() {
        recyclerViewAdapter = RecyclerViewAdapter()
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = recyclerViewAdapter
        }
        val itemSwipe = ItemSwipe()
        itemSwipe.setOnSwipeListener { position ->
            showDeleteAlertPopup(position)
            Log.e("TAG", "onViewCreated: ITEM SWIPE")
        }

        val itemTouchHelper = ItemTouchHelper(itemSwipe)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    /**
     * Swipe Delete işlemi yapılırken "Popup" açılıyor.
     * @author Hakan Akbudak
     * @since 26.08.2022
     * */
    private fun showDeleteAlertPopup(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Uyarı!")
            .setMessage("Silmek istediğine emin misin?")
            .setCancelable(true)
            .setPositiveButton("Evet") { dialogInterface, it ->
                deleteAlertPositiveButtonClickEvent(position)
            }
            .setNegativeButton("Hayır") { dialogInterface, it ->
                dialogInterface.dismiss()
            }.show()
    }

    private fun deleteAlertPositiveButtonClickEvent(position: Int) {
        val user = recyclerViewAdapter.userList[position]
        userViewModel.deleteUser(user)
        recyclerViewAdapter.deleteItem(user)

        Snackbar.make(binding.root, "Item Deleted", Snackbar.LENGTH_SHORT).show()
    }

    private fun initialize() {
        userViewModel = UserViewModel(requireContext())
    }

    /**LirtFragment'tan fabBtn ile EventFragmenta geçiş
     * @author Hakan Akbudak
     * @since 01.09.2022
     * */
    private fun handleClickEvents() {
        recyclerViewAdapter.setOnClickListener { position, user ->
            val bundle = Bundle()
            bundle.putParcelable("user", user)
            findNavController().navigate(R.id.action_listFragment_to_eventFragment, bundle)
        }

        binding.fabBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_eventFragment)
        }
    }


}