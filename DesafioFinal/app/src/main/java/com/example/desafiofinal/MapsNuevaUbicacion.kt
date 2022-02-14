package com.example.desafiofinal

import Auxiliar.InfoLogin
import Modelo.Asistente
import Modelo.Evento
import Modelo.Ubicacion
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.desafiofinal.databinding.ActivityMapsNuevaUbicacionBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MapsNuevaUbicacion : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private val LOCATION_REQUEST_CODE: Int = 0
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsNuevaUbicacionBinding

    private val db = Firebase.firestore
    private val TAG = "Daniel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_nueva_ubicacion)

        createMapFragment()
    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        //Se pueden seleccionar varios tiops de mapas:
        //  None --> no muestra nada, solo los marcadores. (MAP_TYPE_NONE)
        //  Normal --> El mapa por defecto. (MAP_TYPE_NORMAL)
        //  Satélite --> Mapa por satélite.  (MAP_TYPE_SATELLITE)
        //  Híbrido --> Mapa híbrido entre Normal y Satélite. (MAP_TYPE_HYBRID) Muestra satélite y mapas de carretera, ríos, pueblos, etc... asociados.
        //  Terreno --> Mapa de terrenos con datos topográficos. (MAP_TYPE_TERRAIN)
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        //map.setOnMyLocationButtonClickListener(this)
        //map.setOnMyLocationClickListener(this)
        //map.setOnPoiClickListener(this)
        map.setOnMapLongClickListener (this)
        //map.setOnMarkerClickListener(this)

        var ev: Evento = intent.getSerializableExtra("ev") as Evento
        val ubiEvento = LatLng(ev.lat,ev.long) //Inicia en la posicion principal del evento, cerca del cual deberian estar las ubicaciones destacadas

        //map.addMarker(MarkerOptions().position(posEvento).title("Marcador evento"))
        map.moveCamera(CameraUpdateFactory.newLatLng(ubiEvento))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubiEvento,13f))

    }

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    @SuppressLint("MissingSuperCall", "MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    /**
     * Con el parámetro crearemos un marcador nuevo. Este evento se lanzará al hacer un long click en alguna parte del mapa.
     */
    override fun onMapLongClick(p0: LatLng) {
        //map.addMarker(MarkerOptions().position(p0).title("Nuevo marcador"))
        val lat = p0.latitude
        val long = p0.longitude
        Toast.makeText(this, lat.toString() + " " + long.toString(), Toast.LENGTH_LONG).show()


        var ev: Evento = intent.getSerializableExtra("ev") as Evento
        var desc: String = intent.getStringExtra("desc") as String

        var ubi: Ubicacion = Ubicacion(desc,p0.latitude,p0.longitude)

        ev.ubicaciones.add(ubi)

        Toast.makeText(this, "Ubicacion registrada", Toast.LENGTH_LONG).show()
        db.collection("eventos").document(ev.id).update("ubicaciones",ev.ubicaciones)

        var intentV1 = Intent(this, InfoEventoSeleccionado::class.java)
        intentV1.putExtra("ev",ev)
        startActivity(intentV1)

        finish()
    }


    fun createMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        /*
        getMapAsync(this) necesita que nuestra activity implemente la función onMapReady() y para ello tenemos que añadir la interfaz
        OnMapReadyCallback.
         */
    }
}