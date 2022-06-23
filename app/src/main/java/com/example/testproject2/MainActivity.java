package com.example.testproject2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Cache;
import com.example.testproject2.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;




public class MainActivity extends AppCompatActivity {

    private static final String DELIMITER=";";
    private static final String SEPARATOR="\n";
    private  static final String HEADER ="DATE;HEURE;NIVEAU";
    private RecyclerView rv;
    private Button Chart;
    SwipeRefreshLayout refrechl;
    String date, niveau,heure;
    GetData getData;
    FirebaseDatabase db;
    DatabaseReference reference;
    DataBase database;
    private static final String  JSON_URL = "https://api.thingspeak.com/channels/1736439/feeds.json?api_key=VLTJJO4OABBSXHMA&results=60";
   //ArrayList<HashMap<String, String>> chart;
    ArrayList<Data> NiVE;
    FileWriter file = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Chart = findViewById(R.id.chart);
        rv=findViewById(R.id.recycleview);
        refrechl=findViewById(R.id.refrechl);
        database = new DataBase(this);
        NiVE = new ArrayList<>();

        if(database.insertData(NiVE))
        {
            Toast.makeText(this, "sucess", Toast.LENGTH_SHORT).show();
        }
        GetData getData = new GetData();
        getData.execute();

        MainActivity.this.refrech(5000);
        refrechl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onRefresh() {
                refrechl.setRefreshing(false);
                //your code on swipe refresh
                //we are checking networking connectivity
                boolean connection=isNetworkAvailable();
                if(connection){
                    MainActivity.this.recreate();
                }
                else{
                    Toast.makeText(MainActivity.this, "no connexion", Toast.LENGTH_SHORT).show();
                }

            }
        });



        /*Chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ArrayList<Data> d =database.information();
                Intent i =new Intent(getApplicationContext(),BaseActivityData.class);
                i.putExtra("m", d);
                startActivity(i);
                Intent i =new Intent(MainActivity.this,BaseActivityData.class);
                startActivity(i);
            }
        });*/

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refrech:
                Intent inte = new Intent(MainActivity.this, MainActivity.class);
                startActivity(inte);
                return true;
            case R.id.action_deconnexion:
                Toast.makeText(this, "Déconnexion", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent inteent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(inteent);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }
    private void refrech(int miliseconds){
        final Handler handler = new Handler();
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                MainActivity.this.refrech(miliseconds);
            }
        };
    }

        void creerfichierCsv(Data d){
        ArrayList<Data> aarry= new ArrayList<>();
        aarry.add(d);

            try{
                file = new FileWriter("Niveau.csv");
                file.append("head");
                file.append(SEPARATOR);
                Iterator it =aarry.iterator();
                while(it.hasNext()){
                    Data dta =(Data)it.next();
                    file.append(d.getDate());
                    file.append(DELIMITER);
                    file.append(d.getHeure());
                    file.append(DELIMITER);
                    file.append(d.getNiveau());
                    file.append(SEPARATOR);
                }file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }








        public class GetData extends AsyncTask<String, String,String> {

    Data NIVO,M;
        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            HttpURLConnection urlConnection = null;
            try {
                URL url;
                urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();


                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader inr = new InputStreamReader(in);

                    int data = inr.read();
                    while (data != -1) {
                        current = current + (char) data;
                        data = inr.read();
                    }
                    return current;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();

                }
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonobject = new JSONObject(s);
                JSONArray jsonArray = jsonobject.getJSONArray("feeds");
                /*µ** METHODE µ****/
                JSONObject jsonObject2 = jsonArray.getJSONObject(jsonArray.length()-1);
                date = jsonObject2.getString("field2");
                niveau = jsonObject2.getString("field1");
                heure = jsonObject2.getString("field3");
                for (int i = jsonArray.length()-1; i >=0; i--) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    date = jsonObject1.getString("field2");
                    niveau = jsonObject1.getString("field1");
                    heure = jsonObject1.getString("field3");
                    sendData(new Data(niveau,date,heure));
                  //  database.insertData(new Data(niveau,date,heure));
                    //Hashmap
                    //HashMap<String, String> NIVO = new HashMap<>();
                    NIVO=new Data(niveau,date,heure);
                    //database.insertData(NIVO);
                    //NIVO.put("niveau", niveau);
                    NiVE.add(NIVO);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /***************************affichage de résultat sous forme RecyclerView ***************************/

            RbAdapter adapter = new RbAdapter(MainActivity.this,
                    NiVE);
            RecyclerView.LayoutManager lm =new LinearLayoutManager(MainActivity.this);
            rv.setLayoutManager(lm);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

        /********************** send data to firebase real time *********************/
        void sendData(Data data){
            db =FirebaseDatabase.getInstance();
            reference =db.getReference("DATA");
            String id =reference.push().getKey();
            reference.child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });

        }



    }
}