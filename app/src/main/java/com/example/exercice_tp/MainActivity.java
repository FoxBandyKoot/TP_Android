package com.example.exercice_tp;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentTransaction;
        import androidx.recyclerview.widget.ItemTouchHelper;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import com.example.exercice_tp.DTO.NoteDTO;
        import com.example.exercice_tp.memo.ItemTouchHelperCallback;
        import com.example.exercice_tp.memo.NotesAdapter;
        import com.example.exercice_tp.modeles.Note;
        import com.google.gson.Gson;
        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.AsyncHttpResponseHandler;
        import com.loopj.android.http.RequestParams;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getName();

    List<Note> listNotes = new ArrayList<>();
    NotesAdapter notesAdapter = new NotesAdapter(listNotes, this);
    EditText editTextNote;
    RecyclerView recyclerView;
    Intent intent;

    // client HTTP :
    AsyncHttpClient client = new AsyncHttpClient();
    // paramètres :
    RequestParams requestParams = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // init :
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.liste_notes);
        editTextNote = findViewById(R.id.etNote);

        Button bouton = findViewById(R.id.buttonAddNote);
        bouton.setOnClickListener(this);

        /* START - POUR LES INTERACTIONS ET AFFICHAGE DU MEMO */

        // à ajouter pour de meilleures performances :
        recyclerView.setHasFixedSize(true);

        // layout manager, décrivant comment les items sont disposés :
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // adapter :
        recyclerView.setAdapter(notesAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelperCallback(notesAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        /* END - POUR LES INTERACTIONS ET AFFICHAGE DU MEMO */

        /* START - POUR LES DETAILS DU MEMO */


        intent = new Intent(recyclerView.getContext(), DetailActivity.class);


        /* END - POUR LES DETAILS DU MEMO */




    }

    @Override
    public void onClick(View v) {
        final String libelleMemo = editTextNote.getText().toString();
        requestParams.put("memo", libelleMemo);


        // appel :
        client.post("http://httpbin.org/post", requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // retour du webservice :
                String retour = new String(responseBody);
                Log.i("retour ", retour);

                // conversion en un objet Java (à faire!) ayant le même format que le JSON :
                Gson gson = new Gson();

                // TO FINISH
                NoteDTO noteDTO = gson.fromJson(retour, NoteDTO.class);
                // TODO -> affichage d'un attribut DANS UN TOAST APRES:
               /* Log.i(TAG, noteDTO.libelle);*/

                // Display it screen
                listNotes.add(0, new Note(libelleMemo));
                recyclerView.setAdapter(notesAdapter);

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error){
                    Log.e(TAG, error.toString());
            }

        });
    }

    public void openDetails(View v){
               startActivity(intent);
    }

}
