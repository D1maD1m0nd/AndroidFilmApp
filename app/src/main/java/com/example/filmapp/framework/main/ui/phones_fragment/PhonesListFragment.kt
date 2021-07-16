package com.example.filmapp.framework.main.ui.phones_fragment


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentPhonesListBinding


class PhonesListFragment : Fragment() {
    private var _binding: FragmentPhonesListBinding? = null
    private val binding get() = _binding!!

    private val permissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            getContacts()
        } else {
            Toast.makeText(context, getString(R.string.not_permission), Toast.LENGTH_SHORT).show()
        }
    }

    private val permissionResultPhone = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            Toast.makeText(context, getString(R.string.Ok), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, getString(R.string.not_ok), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhonesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        permissionResultPhone.launch(Manifest.permission.CALL_PHONE)
        permissionResultPhone.launch(Manifest.permission.CALL_PRIVILEGED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermission() {
        context?.let {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) -> {
                    getContacts()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun getContacts() {
        var numbers = ArrayList<String>()
        context?.let {
            val cursorWithPhoneNumberContacts: Cursor? = it.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone._ID + " = " + id,
                null,
                null
            )
            cursorWithPhoneNumberContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        numbers.add(
                            cursorWithPhoneNumberContacts.getString(
                                cursorWithPhoneNumberContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                        )
                    }
                }
            }

            cursorWithPhoneNumberContacts?.close()
            val cursorWithContactsContract: Cursor? = it.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursorWithContactsContract?.let { cursor ->
                var phoneNumber = ""
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        )
                        val contactId = cursor.getString(
                            cursor.getColumnIndex(
                                ContactsContract.Contacts._ID
                            )
                        )

                        val hasPhone =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        if (hasPhone == TRUE_PHONE) {
                            val phones: Cursor? = it.contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                null,
                                null
                            )
                            while (phones!!.moveToNext()) {
                                phoneNumber =
                                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            }
                            phones.close()
                        }
                        addView(name, phoneNumber)
                    }
                }
            }
            cursorWithContactsContract?.close()
        }
    }

    private fun addView(name: String, phone: String) = with(binding) {
        containerForContacts.addView(
            AppCompatTextView(requireContext()).apply {
                text = "$name $phone"
                textSize = resources.getDimension(R.dimen.text_7_sp)
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:$phone")
                    startActivity(intent)
                }
            }
        )
    }

    private fun requestPermission() {
        permissionResult.launch(Manifest.permission.READ_CONTACTS)
    }

    companion object {
        const val TRUE_PHONE = "1"

        @JvmStatic
        fun newInstance() =
            PhonesListFragment()
    }
}