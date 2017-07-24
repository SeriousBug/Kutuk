/*
 * Copyright 2017 Kaan Genç
 *
 * This file is part of Kütük.
 *
 * Kütük is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kütük is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kütük.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.kaangenc.ktk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.Realm;
import me.kaangenc.ktk.data.Category;


public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private RecyclerView categoryView;
    private NamedViewAdapter<Category> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        categoryView = (RecyclerView) findViewById(R.id.categories);

        adapter = new NamedViewAdapter<>(realm.where(Category.class).findAll());
        categoryView.setLayoutManager(new LinearLayoutManager(this));
        categoryView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        realm = null;
    }

    public void addCategory(View view) {
        CreateNamedDialog createCategory = new CreateNamedDialog(R.string.category, this, new Category.RealmFactory());
        createCategory.show();
    }
}
