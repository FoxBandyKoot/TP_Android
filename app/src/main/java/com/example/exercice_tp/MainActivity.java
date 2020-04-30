package com.example.exercice_tp;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.ItemTouchHelper;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.exercice_tp.DAO.AppDatabaseHelper;
        import com.example.exercice_tp.DTO.NoteDTO;
        import com.example.exercice_tp.memo.ItemTouchHelperCallback;
        import com.example.exercice_tp.memo.NotesAdapter;
        import com.google.gson.Gson;
        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.AsyncHttpResponseHandler;
        import com.loopj.android.http.RequestParams;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getName();
    private List<NoteDTO> listNotes;
    private NotesAdapter notesAdapter;
    private EditText editTextNote;
    private Button bouton;
    private RecyclerView recyclerView;
    private TextView note;
    private Intent intent;
    private int maxChar;
    private int charWrited;
    private AsyncHttpClient client = new AsyncHttpClient();
    private RequestParams requestParams = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.liste_notes);
        editTextNote = findViewById(R.id.etNote);
        bouton = findViewById(R.id.buttonAddNote);

        final LayoutInflater factory = getLayoutInflater();
        final View textEntryView = factory.inflate(R.layout.note_items_liste, null);
        note = textEntryView.findViewById(R.id.libelle_note);

        // Get data
        listNotes = AppDatabaseHelper.getDatabase(this).noteDAO().getListeNotes();
        notesAdapter = new NotesAdapter(listNotes, this);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setHasFixedSize(true);

        /* START - INTERACTIONS ET AFFICHAGE DES NOTES */
        // Décrit la disposition des items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        checkLimitCharacter();
        bouton.setOnClickListener(this); // Create note

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(notesAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        /* END - INTERACTIONS ET AFFICHAGE DES NOTES */
    }

    /**
     * Check limit character
     */
    public void checkLimitCharacter(){
        maxChar = 30;
        charWrited = 0;
        editTextNote.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                charWrited = editTextNote.length();
                if (charWrited == maxChar){
                    Toast.makeText(getApplicationContext(), "30 characters maximum", Toast.LENGTH_LONG).show();
                    bouton.setEnabled(false);
                } else {
                    bouton.setEnabled(true);
                }
            }
            /* Unused methods */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    /**
     * Create note
     * @param v
     */
    @Override
    public void onClick(View v) {
        final NoteDTO noteDTO = new NoteDTO(editTextNote.getText().toString());
        // Display it screen
        listNotes.add(noteDTO);
        notesAdapter.notifyItemInserted(listNotes.size());
        AppDatabaseHelper.getDatabase(getApplicationContext()).noteDAO().insert(noteDTO); // Save to Android Database
    }

    /**
     * Code pour bdd externe
     * @param v
     */
    public void postToWebService(View v){
        final NoteDTO noteDTO = new NoteDTO(note.getText().toString());
        requestParams.put("libelle", noteDTO.libelle);
        client.post("http://httpbin.org/post", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                String retour = new String(responseBody);
                Gson gson = new Gson();
                JSONObject jsonObject;
                NoteDTO noteDTOBack = new NoteDTO();

                try {
                    jsonObject = new JSONObject(retour);
                    noteDTOBack = gson.fromJson(String.valueOf(jsonObject.getJSONObject("form")), NoteDTO.class); // Retourne null :/

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (noteDTOBack.libelle != null && noteDTOBack.libelle.length() >= 1){
                    Toast.makeText(getApplicationContext(), noteDTOBack.libelle, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error){
                Log.e(TAG, error.toString());
            }
        });
    }
}