package com.example.desafiomicolornote

import Auxiliar.ConexionBD
import Modelo.Contacto
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.PermissionRequest
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class EnviarSMS : AppCompatActivity() {

    private val permissionRequest = 101

    val REQUEST_READ_CONTACTS = 79

    var mobileArray: ArrayList<String> = ArrayList()
    var listaContactos: ArrayList<Contacto> = ArrayList()
    var seleccionado=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviar_sms)


        var contenido:String=intent.getStringExtra("texto") as String

        var txtSMS:TextView=findViewById(R.id.txtTextoSMS)

        var txtNumCont:TextView=findViewById(R.id.txtNumCont)
        var txtNomCont:TextView=findViewById(R.id.txtNomCont)
        var lista: ListView = findViewById(R.id.lsvContactos)
        var botonEnviarSMS: Button = findViewById(R.id.btnEnviarSMS)

        txtSMS.text=contenido

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            listaContactos = this.obtenerListaContactos()

            for(contacto in listaContactos){
                mobileArray.add(contacto.nombre)
            }

        } else {
            requestPermission();
        }

        var adaptador: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.lista_contactos, R.id.txtContacto, mobileArray)

        lista.adapter = adaptador

        lista.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {


                if(seleccionado==pos){
                    seleccionado=-1
                }else{
                    seleccionado=pos
                }

                if(seleccionado==-1){
                    txtNumCont.text = "-"
                    txtNomCont.text = "-"
                }else{
                    if(listaContactos!!.get(seleccionado).telefonos!!.size==0){
                        txtNumCont.text = "-"
                        txtNomCont.text = listaContactos!!.get(seleccionado).nombre

                    }else{

                        txtNumCont.text = listaContactos!!.get(seleccionado).telefonos!![0]
                        txtNomCont.text = listaContactos!!.get(seleccionado).nombre

                    }
                    //txtNumTelefono.text=ContactsContract.CommonDataKinds.Phone.NUMBER
                }

            }
        }

    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),REQUEST_READ_CONTACTS)
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),REQUEST_READ_CONTACTS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mobileArray = this.obtenerContactos()
            } else {
                Toast.makeText(
                    this, "No tienes los permisos requeridos...",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    fun obtenerContactos():ArrayList<String> {
        var listaNombre:ArrayList<String>? = ArrayList()
        var cr = this.contentResolver
        var cur: Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null,null)
        if (cur != null){
            if (cur.count > 0){
                while(cur!=null && cur.moveToNext()){
                    var id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID).toInt())
                    var nombre = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME).toInt())
                    listaNombre!!.add(nombre)
                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER).toInt()) > 0) {
                        val pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)
                        //Sacamos todos los números de ese contacto.
                        while (pCur!!.moveToNext()) {
                            val phoneNo = pCur!!.getString(pCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER).toInt())
                            //Esto son los números asociados a ese contacto. Ahora mismo no hacemos nada con ellos.

                        }
                        pCur!!.close()
                    }
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return listaNombre!!
    }

    fun obtenerListaContactos():ArrayList<Contacto> {
        var listaContactos:ArrayList<Contacto>? = ArrayList()
        var cr = this.contentResolver
        var cur: Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null,null)
        if (cur != null){
            if (cur.count > 0){
                while(cur!=null && cur.moveToNext()){
                    var id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID).toInt())
                    var nombre = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME).toInt())
                    //listaNombre!!.add(nombre)
                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER).toInt()) > 0) {
                        val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)
                        //Sacamos todos los números de ese contacto.
                        var listaNumTlfn:ArrayList<String>? = ArrayList()
                        while (pCur!!.moveToNext()) {
                            val phoneNo = pCur!!.getString(pCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER).toInt())
                            listaNumTlfn!!.add(phoneNo)
                            //Esto son los números asociados a ese contacto. Ahora mismo no hacemos nada con ellos.

                        }
                        var c:Contacto= Contacto(id,nombre,listaNumTlfn)
                        listaContactos!!.add(c)
                        pCur!!.close()
                    }
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return listaContactos!!
    }


    fun enviar(view: View){
        val pm = this.packageManager
        //Esta es una comprobación previa para ver si mi dispositivo puede enviar sms o no.
        if (!pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY) && !pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA)) {
            Toast.makeText(this,"Lo sentimos, tu dispositivo probablemente no pueda enviar SMS...",Toast.LENGTH_SHORT).show()
        }
        else {
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                miMensaje()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), permissionRequest)
            }
        }
    }

    private fun miMensaje() {

        var txtSMS:TextView=findViewById(R.id.txtTextoSMS)
        var txtNumCont:TextView=findViewById(R.id.txtNumCont)
        var myNumber: String
        if(txtNumCont.text.startsWith('+')){
            myNumber = txtNumCont.text.toString().trim().substring(1)
        }else{
            myNumber = txtNumCont.text.toString().trim()
        }


        val myMsg: String = txtSMS.text.toString()
        if (myNumber == "" || myMsg == "") {
            Toast.makeText(this, R.string.txtCamposVacios, Toast.LENGTH_SHORT).show()
        } else {
            if (TextUtils.isDigitsOnly(myNumber)) {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(myNumber, null, myMsg, null, null)
                Toast.makeText(this, R.string.txtSMSEnviado, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.txtNumIncorrecto, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun volver(view:View){
        finish()
    }

}