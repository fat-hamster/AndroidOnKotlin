package com.dmgpersonal.androidonkotlin.lesson9

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dmgpersonal.androidonkotlin.databinding.FragmentContactsBinding
import com.dmgpersonal.androidonkotlin.view.cities.CitiesListFragment

class ContactsFragment: Fragment() {

    private lateinit var binding: FragmentContactsBinding

    companion object {
        fun newInstance() = ContactsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()
    }

    private fun checkPermission() {
        val permResult = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
        if (permResult == PackageManager.PERMISSION_GRANTED) {
            getContacts()
        } else {
            permissionRequest(Manifest.permission.READ_CONTACTS)
        }
    }

    private val REQUEST_CODE_READ_CONTACTS = 687

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE_READ_CONTACTS
            && grantResults[permissions.indexOf(Manifest.permission.READ_CONTACTS)]
            == PackageManager.PERMISSION_GRANTED) {
             getContacts()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_READ_CONTACTS)
    }

    @SuppressLint("Recycle")
    private fun getContacts() {
        val contextResolver = requireContext().contentResolver
        val cursorWithContacts: Cursor? = contextResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
    }
}