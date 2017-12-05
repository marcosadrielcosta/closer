package com.example.marco.biblia.Leitura;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.example.marco.biblia.Login.Login;
import com.example.marco.biblia.Notas.Lista;
import com.example.marco.biblia.Notas.Nova;
import com.example.marco.biblia.Notas.NovaLeitura;
import com.example.marco.biblia.Perfil.EditarPerfil;
import com.example.marco.biblia.Perfil.Perfil;
import com.example.marco.biblia.Plan.Plan;
import com.example.marco.biblia.R;
import com.example.marco.biblia.VersiculoMarcado.MySQLiteHelperVersiculo;
import com.example.marco.biblia.VersiculoMarcado.Versiculo;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Leitura extends AppCompatActivity {
    public static final String PREFS_CAP = "Cap";
    public static String versiculo;
    MySQLiteHelperVersiculo db;

    int capitulo;

    ArrayAdapter<CharSequence> adLivro, adCap, adVersao;
    ArrayAdapter<CharSequence> adGenesis, adExodo;
    ArrayAdapter<CharSequence> adLeitura;

    String livro, data;
    Livros livros = new Livros();
    private Spinner spLivro, spCap, spVer;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitura);

        db = new MySQLiteHelperVersiculo(this);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
        data = sdf.format(date);


        livro = livros.livro;


        spLivro = (Spinner) findViewById(R.id.spLivro);
        spCap = (Spinner) findViewById(R.id.spCap);
        spVer = (Spinner) findViewById(R.id.spVersao);
        lv = (ListView) findViewById(R.id.lvLeitura);
        registerForContextMenu(lv);

        adVersao = ArrayAdapter.createFromResource
                (this, R.array.versao, android.R.layout.simple_list_item_1);
        spVer.setAdapter(adVersao);

        adGenesis = ArrayAdapter.createFromResource
                (this, R.array.capGenesis, android.R.layout.simple_list_item_1);

        adExodo = ArrayAdapter.createFromResource
                (this, R.array.capExodo, android.R.layout.simple_list_item_1);

        adLivro = ArrayAdapter.createFromResource
                (this, R.array.livros, android.R.layout.simple_list_item_1);
        spLivro.setAdapter(adLivro);

        spVer.setSelection(1);
        spVer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    Intent intent = new Intent(getApplicationContext(), LeituraARA.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //******************************************************************************************
        if (livro != null) {
            switch (livro) {
                case "genesis":
                    spLivro.setAdapter(adLivro);
                    adCap = ArrayAdapter.createFromResource
                            (this, R.array.capGenesis, android.R.layout.simple_list_item_1);
                    spCap.setAdapter(adCap);

                    break;

                case "exodo":
                    spLivro.setSelection(1);
                    adCap = ArrayAdapter.createFromResource
                            (this, R.array.capExodo, android.R.layout.simple_list_item_1);
                    spCap.setAdapter(adCap);

                    break;
            }
        }

        //******************************************************************************************
        spLivro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        adLeitura = ArrayAdapter.createFromResource
                                (getApplicationContext(), R.array.genesis1, android.R.layout.simple_list_item_1);
                        lv.setAdapter(adLeitura);
                        spCap.setAdapter(adGenesis);

                        spCap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch (position) {
                                    case 0:
                                        adLeitura = ArrayAdapter.createFromResource
                                                (getApplicationContext(), R.array.genesis1, android.R.layout.simple_list_item_1);
                                        lv.setAdapter(adLeitura);

                                        break;
                                    case 1:
                                        adLeitura = ArrayAdapter.createFromResource
                                                (getApplicationContext(), R.array.genesis2, android.R.layout.simple_list_item_1);
                                        lv.setAdapter(adLeitura);
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case 1:
                        adLeitura = ArrayAdapter.createFromResource
                                (getApplicationContext(), R.array.exodo1, android.R.layout.simple_list_item_1);
                        lv.setAdapter(adLeitura);

                        spCap.setAdapter(adExodo);

                        spCap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch (position) {
                                    case 0:
                                        adLeitura = ArrayAdapter.createFromResource
                                                (getApplicationContext(), R.array.exodo1, android.R.layout.simple_list_item_1);
                                        lv.setAdapter(adLeitura);
                                        break;
                                    case 1:
                                        adLeitura = ArrayAdapter.createFromResource
                                                (getApplicationContext(), R.array.exodo2, android.R.layout.simple_list_item_1);
                                        lv.setAdapter(adLeitura);
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("O que deseja fazer?");
        menu.add(0, v.getId(), 0, "Marcar Versículo");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Criar Nota com o Versículo");
        menu.add(0, v.getId(), 0, "Criar Nota com o Capítulo");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;

        if (item.getTitle() == "Criar Nota com o Versículo") {
            Spinner spinner = (Spinner) findViewById(R.id.spCap);
            String cap = spinner.getSelectedItem().toString();

            Object obj = lv.getAdapter().getItem(index);
            String str = obj.toString() + " (Gênesis " + cap + ":" + (index - 1) + ")";
            versiculo = str;

            Intent intent = new Intent(getApplicationContext(), NovaLeitura.class);
            startActivity(intent);

        } else if (item.getTitle() == "Marcar Versículo") {
            Spinner spinner = (Spinner) findViewById(R.id.spCap);
            String cap = spinner.getSelectedItem().toString();
            Object obj = lv.getAdapter().getItem(index);
            String str = obj.toString() + " (Gênesis " + cap + ":" + (index - 1) + ")";
            db.addVer(new Versiculo(str, data));
            Toast.makeText(getApplicationContext(), "Versículo adicionado aos marcados", Toast.LENGTH_SHORT).show();


        } else if (item.getTitle() == "Criar Nota com o Capítulo") {
            Spinner spinner = (Spinner) findViewById(R.id.spCap);
            String cap = spinner.getSelectedItem().toString();
            String str = " Gênesis " + cap;
            versiculo = str;
            Intent intent = new Intent(getApplicationContext(), NovaLeitura.class);
            startActivity(intent);
        } else {
            return false;
        }
        return true;
    }

    public void clickPerfil(View v) {
        Intent i = new Intent(this, Perfil.class);
        startActivity(i);
    }

    public void clickLeitura(View v) {
        Intent i = new Intent(this, Livros.class);
        startActivity(i);
    }

    public void clickNotas(View v) {
        Intent i = new Intent(this, Lista.class);
        startActivity(i);
    }

    public void clickPlan(View v) {
        Intent i = new Intent(this, Plan.class);
        startActivity(i);
    }
}
