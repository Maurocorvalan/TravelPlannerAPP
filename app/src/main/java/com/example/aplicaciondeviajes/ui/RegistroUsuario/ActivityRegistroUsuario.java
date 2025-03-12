package com.example.aplicaciondeviajes.ui.RegistroUsuario;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplicaciondeviajes.databinding.ActivityRegistroUsuarioBinding;


public class ActivityRegistroUsuario extends AppCompatActivity {

    private ActivityRegistroUsuarioBinding binding;
    private ActivityRegistroUsuarioViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ActivityRegistroUsuarioViewModel.class);

        // Observar el mensaje
        viewModel.getMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String mensaje) {
                Toast.makeText(ActivityRegistroUsuario.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.editNombre.getText().toString();
                String correo = binding.editCorreo.getText().toString();
                String contrasena = binding.editContrasenia.getText().toString();

                if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(ActivityRegistroUsuario.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                }
                else if (!esCorreoValido(correo)) {
                    Toast.makeText(ActivityRegistroUsuario.this, "Por favor, ingrese un correo válido.", Toast.LENGTH_SHORT).show();
                }
                else {
                    viewModel.registrarUsuario(nombre, correo, contrasena);
                }
            }
        });
    }
    private boolean esCorreoValido(String correo) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return correo.matches(regex);
    }

}
