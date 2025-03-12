package com.example.aplicaciondeviajes.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.request.ApiClient;
public class AnadirFotoViajeFragment extends Fragment {

    private AnadirFotoViajeViewModel mViewModel;
    private static final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 2;

    private ImageView imageViewFoto;
    private EditText editTextDescripcion;
    private Button btnSubirFoto, btnSeleccionarImagen;
    private Uri imageUri;
    private int viajeId;
    public static AnadirFotoViajeFragment newInstance(Bundle bundle) {
        AnadirFotoViajeFragment fragment = new AnadirFotoViajeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anadir_foto_viaje, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AnadirFotoViajeViewModel.class);

        if (getArguments() != null) {
            viajeId = getArguments().getInt("viajeId");  // Recibir el viajeId aquí
            Log.d("verfotoviaje", "ID del viaje recibido: " + viajeId);
        }

        imageViewFoto = getView().findViewById(R.id.image_foto);
        editTextDescripcion = getView().findViewById(R.id.edit_descripcion_foto);
        btnSeleccionarImagen = getView().findViewById(R.id.btn_seleccionar_imagen);
        btnSubirFoto = getView().findViewById(R.id.btn_subir_foto);

        btnSeleccionarImagen.setOnClickListener(v -> seleccionarImagen());
        btnSubirFoto.setOnClickListener(v -> subirFoto());
    }

    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    private void tomarFoto() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void subirFoto() {
        if (imageUri != null && !editTextDescripcion.getText().toString().isEmpty()) {
            String descripcion = editTextDescripcion.getText().toString();
            String token = ApiClient.leerToken(getContext());  // Obtener el token desde ApiClient
            Log.d("verfotoviaje", "en subirfotofrag"+String.valueOf(viajeId));
            // Utilizar el viajeId que se recibió desde los argumentos
            mViewModel.subirFoto(imageUri, descripcion, token, getContext(), viajeId, getView());
        } else {
            Toast.makeText(getContext(), "Por favor selecciona una foto y escribe una descripción", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                imageUri = data.getData();
            } else if (requestCode == CAMERA_REQUEST) {
                imageUri = data.getData();
            }

            imageViewFoto.setImageURI(imageUri);
        }
    }
}
