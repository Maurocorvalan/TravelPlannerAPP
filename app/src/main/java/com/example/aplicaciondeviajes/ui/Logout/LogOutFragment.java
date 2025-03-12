package com.example.aplicaciondeviajes.ui.Logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aplicaciondeviajes.MainActivity;
import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.databinding.FragmentLogOutBinding;
import com.example.aplicaciondeviajes.request.ApiClient;

public class LogOutFragment extends Fragment {

    private LogOutViewModel mViewModel;
    private FragmentLogOutBinding binding;

    public static LogOutFragment newInstance() {
        return new LogOutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLogOutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btnCerrarSesion = binding.btnSalir;
        showExitDialog();
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    private void showExitDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirmar Salida")
                .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    ApiClient.deslogeo(requireContext());
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

}
