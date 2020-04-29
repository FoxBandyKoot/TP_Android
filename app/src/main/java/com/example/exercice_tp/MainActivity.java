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

    // client HTTP :
    private AsyncHttpClient client = new AsyncHttpClient();
    // paramètres :
    private RequestParams requestParams = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // init :
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.liste_notes);
        editTextNote = findViewById(R.id.etNote);
        bouton = findViewById(R.id.buttonAddNote);
        bouton.setOnClickListener(this);

        final LayoutInflater factory = getLayoutInflater();
        final View textEntryView = factory.inflate(R.layout.note_items_liste, null);
        note = textEntryView.findViewById(R.id.libelle_note);

        // Get data
        listNotes = AppDatabaseHelper.getDatabase(this).noteDAO().getListeNotes();
        notesAdapter = new NotesAdapter(listNotes, this);
        recyclerView.setAdapter(notesAdapter);

        /* START - POUR LES INTERACTIONS ET AFFICHAGE DU MEMO */

        // à ajouter pour de meilleures performances :
        recyclerView.setHasFixedSize(true);

        // Layout manager, décrivant comment les items sont disposés :
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(notesAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        /* END - POUR LES INTERACTIONS ET AFFICHAGE DU MEMO */


        /* START - POUR LES DETAILS DU MEMO */
        intent = new Intent(recyclerView.getContext(), DetailActivity.class);
        /* END - POUR LES DETAILS DU MEMO */

        // Check limit character
        maxChar = 30;
        charWrited = 0;
        final String[] charWrited_S = {null};

        editTextNote.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                charWrited = editTextNote.length();
                if (charWrited == maxChar){
                    Toast.makeText(getApplicationContext(), "30 characters maximum.", Toast.LENGTH_LONG).show();
                    bouton.setEnabled(false);
                } else {
                    bouton.setEnabled(true);
                }
            }
            /* Unused methods */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) { }
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


    public void postToWebServiceAndOpenDetails(View v){
        openDetails();

        postToWebService(v);

    }

    public void openDetails(){
        startActivity(intent);

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

                // TODO -> affichage d'un attribut DANS UN TOAST APRES:
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
