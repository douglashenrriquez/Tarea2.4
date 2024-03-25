package com.example.tarea24;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class AgregarFirma extends AppCompatActivity {

    private static final int REQUEST_SIGNATURE = 1;

    private SignatureView signatureView;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_firma);

        signatureView = findViewById(R.id.signatureView);
        editTextDescription = findViewById(R.id.Description);
        Button buttonSave = findViewById(R.id.btnSalvar);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSignature();
            }
        });
    }

    public void startSignatureActivity(View view) {
        String description = editTextDescription.getText().toString();
        if (!description.isEmpty()) {
            Intent intent = new Intent(this, SignatureView.class);
            startActivityForResult(intent, REQUEST_SIGNATURE);
        } else {
            Toast.makeText(this, "Por favor, escriba una descripción antes de dibujar una firma", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNATURE && resultCode == RESULT_OK) {
            Bitmap signatureBitmap = data.getParcelableExtra("signature");
            signatureView.setSignatureBitmap(signatureBitmap);
        }
    }

    private void saveSignature() {
        String description = editTextDescription.getText().toString();
        Bitmap signatureBitmap = signatureView.getSignatureBitmap();
        if (signatureBitmap != null && !description.isEmpty()) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("image", byteArray);
            resultIntent.putExtra("description", description);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Por favor, dibuje una firma y escriba una descripción", Toast.LENGTH_SHORT).show();
        }
    }
}
