package com.example.barkod;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button button,barcodeButton,pushButton,denemeButton;
    private Button buttonabout;
    private String barcode;
    private ImageView imageXml;
    String imageURL;
    Product data;
    private TextView categoryXml,nameXml,publisherXml,manufacturerXml,priceXml,barcodeXml,authorXml,storeXml,linkXml,availabilityXml;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_barcode);
        setContentView(R.layout.activity_main);
        denemeButton = findViewById(R.id.denemeButton);
        pushButton = findViewById(R.id.pushButton);
        barcodeButton = findViewById(R.id.barcodeButton);
        barcodeXml = findViewById(R.id.barcodeXml);
        authorXml = findViewById(R.id.authorXml);
        storeXml = findViewById(R.id.storeXml);
        imageXml = findViewById(R.id.imageXml);
        linkXml = findViewById(R.id.linkXml);
        publisherXml= findViewById(R.id.publisherXml);
        availabilityXml = findViewById(R.id.availabilityXml);
        nameXml = findViewById(R.id.nameXml);
        manufacturerXml = findViewById(R.id.manufacturerXml);
        priceXml= findViewById(R.id.priceXml);
        categoryXml = findViewById(R.id.categoryXml);
        buttonabout= findViewById(R.id.aboutButton);
        button = findViewById(R.id.scButton);

//        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        myRef.setValue("Hello, World!");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("allah", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("muhabbet", "Failed to read value.", error.toException());
            }
        });

    }
    public void writeNewUser(String title, String category, String manufacturer, String price, String barcode, String author, String store, String link, String availability, String publisher,String image) {
        Product user = new Product(title,category,manufacturer,price,barcode,author,store,link,availability ,publisher, image);

        mDatabase.child("barkod").child(title).setValue(user);
    }
    public void denemeButton(View view){
        Intent intent = new Intent(MainActivity.this,userlist.class);
        startActivity(intent);
    }
    public void scanButton (){
        ScanOptions options = new ScanOptions();
        options.setPrompt("please read a barcode");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(Capture.class);
        barLauncher.launch(options);

    }
    ActivityResultLauncher<ScanOptions> barLauncher=registerForActivityResult(new ScanContract(),  result -> {
        if(result.getContents() !=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result");
            barcode = String.valueOf((result.getContents()));
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }

            }).show();
        }
    });
//    public void BarcodeButton(View view){
//        Intent intent = new Intent(MainActivity.this, Product.class);
//        intent.putExtra("category",data.getCategory());
//        intent.putExtra("title",data.getTitle());
//        intent.putExtra("manufacturer",data.getManufacturer() );
//        intent.putExtra("price",data.getPrice() );
//        intent.putExtra("author",data.getAuthor() );
//        intent.putExtra("publisher",data.getPublisher() );
//        intent.putExtra("barcode",data.getBarcode() );
//        intent.putExtra("link",data.getLink() );
//        intent.putExtra("store",data.getStore());
//        intent.putExtra("availability",data.getAvailability() );
//        intent.putExtra("image",data.getImage() );
//        writeNewUser(data.getTitle(),data.getCategory(),data.getManufacturer(),data.getPrice(),data.getBarcode(),data.getAuthor(),data.getStore(),data.getLink(),data.getAvailability(),data.getPublisher(),data.getImage());
//
//
//
//        startActivity(intent);
//    }
//    public void DenemeButton(View view){
////        Intent intent = new Intent(MainActivity.this,BarcodeList.class);
////        startActivity(intent);
//        ArrayList<BarcodeData> arrayList = new ArrayList<>();
//        arrayList.add(new BarcodeData("asdsad", "sfddsfdsf", "sdfdsfdsf", "sfskjlflkjds"));
//        arrayList.add(new BarcodeData("asdsad", "sfddsfdsf", "sdfdsfdsf", "sfskjlflkjds"));
//        arrayList.add(new BarcodeData("asdsad", "sfddsfdsf", "sdfdsfdsf", "sfskjlflkjds"));
//        arrayList.add(new BarcodeData("asdsad", "sfddsfdsf", "sdfdsfdsf", "sfskjlflkjds"));
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recView);
//        Adapter adapter = new Adapter(getApplicationContext(),arrayList);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//
//    }
    public void pushButton(View view){
        new HttpAsyncTask().execute("https://api.barcodelookup.com/v3/products?barcode="+barcode+"&formatted=y&key=e19vwhag1o2q50ekvu4rbyuua2mflc");


    }
    public void aboutButton(View view){
        startActivity(new Intent(MainActivity.this, AboutClass.class));
    }
    public void FindBarcode(){
        String url = "https://api.barcodelookup.com/v3/products?barcode="+barcode+"&formatted=y&key=ifDzhmKslKav42OD93NE";
        new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //get category
                    JSONArray object = response.getJSONArray("products");
                    JSONObject jsonObject = object.getJSONObject(0);
                    String category = jsonObject.getString("category");
                    categoryXml.setText(category);
                    //get name
                    String title = jsonObject.getString("title");
                    nameXml.setText(title);
                    //get manufacturer
                    String manufacturer = jsonObject.getString("manufacturer");
                    manufacturerXml.setText(manufacturer);
                    //get price
                    JSONArray object3 = jsonObject.getJSONArray("stores");
                    for(int i=0; i<object3.length();i++){
                        JSONObject jsonObject3 = object3.getJSONObject(i);
                        int price = Integer.parseInt(jsonObject3.getString("price"));
                        priceXml.setText(price);
                    }
                    //get author and publisher
                    JSONArray object4 = jsonObject.getJSONArray("contributors");
                    for(int i=0; i<object4.length();i++){
                        JSONObject jsonObject4 = object4.getJSONObject(i);
                        if(jsonObject4.getString("role").equals("author")){
                            String author = jsonObject4.getString("name");
                            Log.d("author",author);
                            authorXml.setText(author);
                        }
                    }
                    //get barcode

                    int barcode = Integer.parseInt(jsonObject.getString("barcode_number"));
                    barcodeXml.setText(barcode);
                    //get store
                    JSONArray object6 = jsonObject.getJSONArray("stores");
                    for(int i=0; i<object6.length();i++){
                        JSONObject jsonObject6 = object6.getJSONObject(i);
                        String store = jsonObject6.getString("name");
                        storeXml.setText(store);
                    }
                    //get link
                    JSONArray object7 = jsonObject.getJSONArray("stores");
                    for(int i=0; i<object7.length();i++){
                        JSONObject jsonObject7 = object7.getJSONObject(i);
                        String link = jsonObject7.getString("link");
                        linkXml.setText(link);
                    }
                    //check availability
                    JSONArray object8 = jsonObject.getJSONArray("stores");
                    for(int i=0; i<object8.length();i++){
                        JSONObject jsonObject8 = object8.getJSONObject(i);
                        String availability = jsonObject8.getString("availability");
                        availabilityXml.setText(availability);
                    }
                    //get image
                    JSONArray object9 = jsonObject.getJSONArray("images");
                    String image = (String) object9.get(0);
                    Picasso.get().load(image).into(imageXml);
                    imageURL=image;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
    public static String Get(String urlString){

        String json = null;
        URL url;
        HttpURLConnection httpURLConnection = null;
        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line ;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
                Log.d("Line:", line);
            }
            bufferedReader.close();
            json=stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void scan(View view) {
        Log.d("Scan","scan");
        scanButton();
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String s) {

            String title="",category="",manufacturer="",price="",publisher="",barcode="",store="",link="",availability="",image="", author="";
            Exception x = null;
            Log.e(s, Log.getStackTraceString(x));

            try {
                //get category
                JSONObject response = new JSONObject(s);
                JSONArray object = response.getJSONArray("products");
                JSONObject jsonObject = object.getJSONObject(0);
                Log.d("Json", jsonObject.getString("title"));
                category = jsonObject.getString("category");
                Log.d("Json", jsonObject.getString("category"));
                //categoryXml.setText(category);
                //get name
                title = jsonObject.getString("title");
                //nameXml.setText(title);
                //get manufacturer
                manufacturer = jsonObject.getString("manufacturer");
                //manufacturerXml.setText(manufacturer);
                //get price
                JSONArray object3 = jsonObject.getJSONArray("stores");
                for(int i=0; i<object3.length();i++){
                    JSONObject jsonObject3 = object3.getJSONObject(i);
                    //int price = Integer.parseInt(jsonObject3.getString("price"));
                    price = jsonObject3.getString("price");
                    Log.d("Price 123123", price);
                }
                //get author and publisher
                JSONArray object4 = jsonObject.getJSONArray("contributors");
                for(int i=0; i<object4.length();i++){
                    JSONObject jsonObject4 = object4.getJSONObject(i);
                    if(jsonObject4.getString("role").equals("author")){
                        author = jsonObject4.getString("name");
                        Log.d("author",author);
                        //authorXml.setText(author);
                    }
                }
                //get barcode

                String barcodeNumber = jsonObject.getString("barcode_number");
                Log.d("barkodB", barcodeNumber);
                //barcodeXml.setText(barcode);
                //get store
                JSONArray object6 = jsonObject.getJSONArray("stores");
                for(int i=0; i<object6.length();i++){
                    JSONObject jsonObject6 = object6.getJSONObject(i);
                    store = jsonObject6.getString("name");
                    Log.d("asdfadsf",store);
                    // storeXml.setText(store);
                }
                //get link
                JSONArray object7 = jsonObject.getJSONArray("stores");
                for(int i=0; i<object7.length();i++){
                    JSONObject jsonObject7 = object7.getJSONObject(i);
                    link = jsonObject7.getString("link");
                    Log.d("asdf",link);

                    //linkXml.setText(link);
                }
                //check availability
                JSONArray object8 = jsonObject.getJSONArray("stores");
                for(int i=0; i<object8.length();i++){
                    JSONObject jsonObject8 = object8.getJSONObject(i);

                    availability = jsonObject8.getString("availability");
                    writeNewUser(title,category,manufacturer,price,barcode,author,store,link,availability,publisher,image);
                    //availabilityXml.setText(availability);
                    Log.d("availability",availability);
                }
                //get image
                JSONArray object9 = jsonObject.getJSONArray("images");
                image = (String) object9.get(0);
                Log.d("asd",image);
                // Picasso.get().load(image).into(imageXml);
                data = new Product(title, category, manufacturer, price, barcodeNumber, author,store, link, availability, publisher, image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d("doInBack", strings[0]);
            return Get(strings[0]);
        }
    }
}





