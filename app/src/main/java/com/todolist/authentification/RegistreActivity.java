package com.todolist.authentification;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

class RegisterActivity extends AppCompatActivity {

    private TextInputEditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registre_activity);

        // Initialisation des vues
        fullNameEditText = findViewById(R.id.fullName);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);

        // Gestion du clic sur le bouton d'inscription
        findViewById(R.id.registerButton).setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        // Récupération des valeurs
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validation des champs
        if (TextUtils.isEmpty(fullName)) {
            showError("Veuillez entrer votre nom complet");
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Veuillez entrer une adresse email valide");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            showError("Le mot de passe doit contenir au moins 6 caractères");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Les mots de passe ne correspondent pas");
            return;
        }

        // Si tout est valide, procéder à l'inscription
        performRegistration(fullName, email, password);
    }

    private void performRegistration(String fullName, String email, String password) {
        // Ici vous implémenteriez la logique d'inscription réelle:
        // - Enregistrement en base de données locale
        // - Ou envoi à un serveur distant

        // Pour l'exemple, nous affichons juste un message
        String successMessage = "Inscription réussie!\n" +
                "Nom: " + fullName + "\n" +
                "Email: " + email;

        Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();

        // Vous pourriez aussi rediriger vers l'activité de connexion
        // finish(); // Ferme cette activité
    }

    private void showError(String errorMessage) {
        TextView errorView = findViewById(R.id.errorMessage);
        errorView.setText(errorMessage);
        errorView.setVisibility(View.VISIBLE);
    }

    // Méthode pour aller à l'écran de connexion
    public void goToLogin(View view) {
        // Intent intent = new Intent(this, LoginActivity.class);
        // startActivity(intent);
        // finish();

        Toast.makeText(this, "Redirection vers la connexion", Toast.LENGTH_SHORT).show();
    }
}
