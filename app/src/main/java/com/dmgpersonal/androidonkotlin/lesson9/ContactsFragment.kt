package com.dmgpersonal.androidonkotlin.lesson9

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dmgpersonal.androidonkotlin.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

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
        val permResult =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к контактам")
                .setMessage("Разрешение требуется для отображения контактов")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionRequest(Manifest.permission.READ_CONTACTS)
                }
                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else if (permResult != PackageManager.PERMISSION_GRANTED) {
            permissionRequest(Manifest.permission.READ_CONTACTS)
        } else {
            getContacts()
        }
    }

    private val REQUEST_CODE_READ_CONTACTS_NAME = 687
    private val REQUEST_CODE_READ_CONTACTS_NUMBER = 688

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_READ_CONTACTS_NAME
            && grantResults[permissions.indexOf(Manifest.permission.READ_CONTACTS)]
            == PackageManager.PERMISSION_GRANTED
        ) {
            getContacts()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_READ_CONTACTS_NAME)
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

        cursorWithContacts?.let { contactsCursor ->
            for (idx in 0 until contactsCursor.count) {
                contactsCursor.moveToPosition(idx)

                val contactName = contactsCursor.getString(
                    contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                )
                val contactID = contactsCursor.getString(
                    contactsCursor
                        .getColumnIndex(ContactsContract.Contacts._ID)
                )
                val contactNumber = getNumberFromID(contextResolver, contactID)
                addView(contactName, contactNumber)
            }
        }
        cursorWithContacts?.close()
    }

    @SuppressLint("Recycle")
    private fun getNumberFromID(cr: ContentResolver, contactId: String): String {
        val phones = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null
        )
        var number: String = "none"
        phones?.let { cursor ->
            while (cursor.moveToNext()) {
                number =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
        }
        return number
    }

    @SuppressLint("SetTextI18n")
    private fun addView(name: String, number: String) {
        with(binding.containerForContacts) {
            addView(AppCompatTextView(requireContext()).apply {
                text = "$name -> $number"
                textSize = 16F
            })
        }
    }
}