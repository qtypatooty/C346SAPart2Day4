package sg.edu.rp.c346.id20006949.c346sapart2_day4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {
ListView lv;
Button button;
    Spinner spinner;
    ArrayAdapter<PSIReading> aa;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ArrayList<String> spinList = new ArrayList<>();
        lv = findViewById(R.id.lv);
        al = new ArrayList<Song>();
        button = findViewById(R.id.button);
        adapter = new CustomAdapter(this, R.layout.row, al);
        ArrayAdapter<String> ab = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,spinList);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = al.get(position);
                Intent i = new Intent(SongList.this,
                        SongDetails.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });
    }
}