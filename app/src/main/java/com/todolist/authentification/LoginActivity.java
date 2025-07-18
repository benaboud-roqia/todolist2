package com.todolist.authentification;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des vues
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        errorMessage = findViewById(R.id.errorMessage);

        // Gestion du clic sur le bouton de connexion
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validation simple
        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Veuillez remplir tous les champs");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }

        // Logique d'authentification (exemple simple)
        if (username.equals("admin") && password.equals("1234")) {
            // Authentification réussie
            Toast.makeText(this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
            errorMessage.setVisibility(View.GONE);
            // Ici vous pourriez démarrer une nouvelle activité
        } else {
            // Authentification échouée
            errorMessage.setText("Identifiants incorrects");
            errorMessage.setVisibility(View.VISIBLE);
        }
    }
}