package com.example.aplicaciondeviajes.ui.resetContrasenia;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplicaciondeviajes.databinding.ActivityResetContraseniaBinding;

public class ActivityResetContrasenia extends AppCompatActivity {

    private ActivityResetContraseniaBinding binding;
    private ActivityResetContraseniaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetContraseniaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ActivityResetContraseniaViewModel.class);

        viewModel.getMensajeRespuesta().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String mensaje) {
                Toast.makeText(ActivityResetContrasenia.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btEnviarResetContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etMailContraReset.getText().toString();
                viewModel.resetearContraseña(email);
            }
        });
    }
}
