package com.example.contentprovider.data

import android.content.*
import android.database.Cursor
import android.database.DatabaseUtils
import android.net.Uri
import android.provider.ContactsContract
import android.provider.UserDictionary
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class ContactRepository(private val context: Context) {
    private val phonePattern = Pattern.compile("\\+?[0-9]{3}-?[0-9]{6,12}")
    private val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]{2,3}")

    suspend fun saveContact(name: String, phone: String, email: String) {
        withContext(Dispatchers.IO) {
            if (phonePattern.matcher(phone).matches().not() || name.isBlank()) {
                throw IncorrectFormException()
            }
            val contactId = saveRawContact()
            saveContactName(contactId, name)
            saveContactPhone(contactId, phone)
            if (email != "" && emailPattern.matcher(email).matches()) {
                saveContactEmail(contactId, email)
                Log.d("ContactRepository","save mail $email")
            }
        }
    }

    suspend fun deleteContact(contact: Contact) {
        withContext(Dispatchers.IO) {

            val uriId = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI,contact.id
            )
//            val uriName = Uri.withAppendedPath()
//            val uriContact = ContactsContract.Contacts.CONTENT_URI
//            val sel = ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?"
//            val selectionArguments = arrayOf(contact.name)
//val cursor = context.contentResolver.query(
//    uriContact,null,sel,selectionArguments,null
//)
//DatabaseUtils.dumpCurrentRowToString(cursor)
//
//
//            //выдает все поля связанные с контактом
//val uri = ContactsContract.Contacts.CONTENT_URI
//            val contactInfo = Uri.withAppendedPath(
//                uri,
//                ContactsContract.Contacts.Entity.CONTENT_DIRECTORY
//            )

//            val selectionClause = "${ContactsContract.Contacts.DISPLAY_NAME} LIKE ?"
//            val selectionArgs = arrayOf(contact.name)
          var rowsDeleted = 0
            rowsDeleted = context.contentResolver.delete(
                uriId,null,null
            )
            Log.e("ContactRepository","delete count $rowsDeleted")
        }
    }


    private fun saveRawContact(): Long {
        val uri = context.contentResolver.insert(
            ContactsContract.RawContacts.CONTENT_URI,
            ContentValues()
        )
        Log.d("saveRawContact", "uri = $uri")
        return uri?.lastPathSegment?.toLongOrNull() ?: error("Cannot save raw contact")
    }

    private fun saveContactEmail(contactId: Long, email: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactName(contactId: Long, name: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactPhone(contactId: Long, phone: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }


    suspend fun getAllContacts(): List<Contact> {
        return withContext(Dispatchers.IO) {
            context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )?.use {
                getContactsFromCursor(it)
            }.orEmpty()
        }
    }

    private fun getContactsFromCursor(cursor: Cursor): List<Contact> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Contact>()
        do {
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val name = cursor.getString(nameIndex).orEmpty()
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val id = cursor.getLong(idIndex)
            list.add(
                Contact(
                    id, name, getPhonesForContact(id), getMailForContact(id)
                )
            )
        } while (cursor.moveToNext())
        return list
    }
    private fun getMailForContact(contactId: Long): List<String>{
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getMailFromCursor(it)
        }.orEmpty()
    }
    private fun getMailFromCursor(cursor: Cursor):List<String>{
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        do {
            val emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
            val email = cursor.getString(emailIndex)
            list.add(email)
        }while (cursor.moveToNext())
        return list
    }

    private fun getPhonesForContact(contactId: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    private fun getPhonesFromCursor(cursor: Cursor): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val phone = cursor.getString(numberIndex).orEmpty()
            list.add(phone)
        } while (cursor.moveToNext())
        return list
    }
}