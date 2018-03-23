package com.example.pc_aulaa.permisossistema;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class Main2Activity extends AppCompatActivity {

        RelativeLayout l;
        Snackbar snak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String mensaje="Faltan otorgar algunos permisos";
        l=findViewById(R.id.milayout);
        snak = Snackbar.make(l,mensaje,Snackbar.LENGTH_INDEFINITE);
        snak.setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Main2Activity.this, new String[]{
                        CAMERA,
                        ACCESS_COARSE_LOCATION,
                        ACCESS_FINE_LOCATION
                }, 100);
            }
        });



        //verifica si los permisos ya se pidieron antes

        if(verificarpermisos())
        {
            iniciarApp();
            //alt + enter    crear method
        }
        else
        {
            solicitarPermisos();
            //antesde activar solicitar permisos ir a gradle y agregar el la tercera fila
            //implementation 'com.android.support:design:26.1.0'
        }
    }

    private void solicitarPermisos() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,ACCESS_COARSE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,ACCESS_FINE_LOCATION) )
        {

            snak.show();
        }

        else
        {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{
                    CAMERA,
                    ACCESS_COARSE_LOCATION,
                    ACCESS_FINE_LOCATION
            }, 100);
        }

    }

    private boolean verificarpermisos() {
        if (Build.VERSION.SDK_INT < 17)
            //solicita version de android
            //este if se ejecuta siempre que las versiones sean menor que el android 6.
            // y luego presionar SYNC NOW
        {
            return true;
        }


        if(ContextCompat.checkSelfPermission(Main2Activity.this,CAMERA)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Main2Activity.this,ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Main2Activity.this,ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        //alt+enter en camera create permission,
        {
            return true;


        }
        return  false;
    }

    private void iniciarApp() {

        Context c= getApplicationContext();
        String m = "La app ya tiene todos los permisos para iniciar";
        Toast.makeText(c,m,Toast.LENGTH_LONG).show();


    }

// Aqui presionar Ctrl + O
    // y buscar onRequestPermissionsResult

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==100)
        {
            if ( grantResults[0]==PackageManager.PERMISSION_GRANTED &&
                    grantResults[1]==PackageManager.PERMISSION_GRANTED &&
                    grantResults[2]==PackageManager.PERMISSION_GRANTED)
            {
                iniciarApp();

            }

            else
            {
                Context c= getApplicationContext();
                String mensaje = "No has otorgado todos los permisos";
                Toast.makeText(c,mensaje,Toast.LENGTH_LONG).show();
                snak.show();


            }
        }
    }
}
