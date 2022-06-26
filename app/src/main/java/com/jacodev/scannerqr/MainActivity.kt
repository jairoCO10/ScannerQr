package com.jacodev.scannerqr

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    
    val MY_PERMISSION_CAMARA = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            solicitarPermisos()
        }
        else {
            agregarEventoBoton()
            Toast.makeText(applicationContext, "Permisos ya concedidos", Toast.LENGTH_LONG).show()
            
        }

        
    }
    fun solicitarPermisos(){
        if(ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), MY_PERMISSION_CAMARA)
            
        }
        else{
            agregarEventoBoton()
            Toast.makeText(applicationContext, "Permisos concedidos anteriormente", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            MY_PERMISSION_CAMARA-> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    agregarEventoBoton()
                    Toast.makeText(applicationContext, "Permisos concedidos", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(applicationContext, "Permisos denegados", Toast.LENGTH_LONG).show()
                }
            }
            else ->{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            }

        }
    }

    fun agregarEventoBoton(){
        btnEscanner.isEnabled = true
        btnEscanner.setOnClickListener{
            IntentIntegrator(this)
                .setPrompt("enfoca un Qr valido")
                .initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result : IntentResult  = IntentIntegrator.parseActivityResult(requestCode,resultCode, data)
        if(result != null ){
            if ( result.contents == null){
                Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_LONG).show()
            }
            else{
                var contenido: String = result.contents
                if(contenido.startsWith("http")){
                    mostrarFrament(1, contenido)

                }
                else if(contenido.contains("VCARD")) {
                    mostrarFrament(2, contenido )

                }

            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun mostrarFrament(opcion: Int, datos: String){
        lateinit var newFragment: Fragment
        when(opcion){
            1-> {
                newFragment= webFragment.newInstance(datos)
            }
            2 -> {
                newFragment = contactFragment.newInstance(datos)
            }
            else -> {
                Toast.makeText(applicationContext, "codigo no valido", Toast.LENGTH_LONG).show()

            }
        }
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contenedor, newFragment)
         transaction.addToBackStack(null)

        Handler().postDelayed({
            transaction.commit()
        },1000)


    }

}