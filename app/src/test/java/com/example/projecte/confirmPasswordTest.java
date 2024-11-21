package com.example.projecte;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecte.components.GroupListAdapter;
import com.example.projecte.components.NewGroupAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class confirmPasswordTest {

    @Test
    public void matchNormalPasswords() {
        assertTrue(passwordEqual("PasswordTest", "PasswordTest"));
    }

    public void matchNormalPasswordsIncorrect() {
        assertFalse(passwordEqual("PasswordTest", "PasswordTestt"));
    }

    @Test
    public void matchNumberPasswords() {
        assertTrue(passwordEqual("PasswordTest20242025", "PasswordTest20242025"));
    }

    @Test
    public void matchNumberPasswordsIncorrect() {
        assertFalse(passwordEqual("PasswordTest20242025", "PasswordTest202420255"));
    }

    @Test
    public void matchEmptyPasswordsIncorrect() {
        assertFalse(passwordEqual("PasswordTest20242025", ""));
    }

    @Test
    public void matchCharsPasswords() {
        assertTrue(passwordEqual("PasswordTest#$%&/()", "PasswordTest#$%&/()"));
    }

    @Test
    public void matchCharsPasswordsIncorrect() {
        assertFalse(passwordEqual("PasswordTest#$%&/()", "PasswordTest#$%&/())"));
    }

    private boolean passwordEqual(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
