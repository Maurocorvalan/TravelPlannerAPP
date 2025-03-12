package com.example.aplicaciondeviajes.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.databinding.FragmentPerfilBinding;
import com.example.aplicaciondeviajes.models.Usuario;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel pvm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        pvm = new ViewModelProvider(this).get(PerfilViewModel.class);
        pvm.getMUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    binding.etNombre.setText(usuario.getNombre());
                    binding.etMail.setText(usuario.getCorreo());
                } else {
                    Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btGuardarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario u = new Usuario(
                        binding.etNombre.getText().toString(),
                        binding.etMail.getText().toString()
                );
                pvm.editarPerfil(u);
            }
        });

        binding.btCambiarContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etContraActual.setVisibility(View.VISIBLE);
                binding.etContraConfirmar.setVisibility(View.VISIBLE);
                binding.etContraNueva.setVisibility(View.VISIBLE);
                binding.btAplicarContra.setVisibility(View.VISIBLE);
            }
        });
        binding.btAplicarContra.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String contraseñaActual = binding.etContraActual.getText().toString();
                String nuevaContraseña = binding.etContraNueva.getText().toString();
                String confirmarContraseña = binding.etContraConfirmar.getText().toString();

                pvm.cambiarContraseña(contraseñaActual, nuevaContraseña, confirmarContraseña);
            }

        });

        pvm.llenarPerfil();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}