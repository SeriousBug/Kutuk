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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.Math.random;



public class MainActivity extends AppCompatActivity {
    private ArrayList<String> maleNames = new ArrayList<>();
    private ArrayList<String> femaleNames = new ArrayList<>();
    private ArrayList<String> surnames = new ArrayList<>();

    private void readNames(ArrayList<String> names, int resource) {
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                getResources().openRawResource(resource)
                        )
                );
        try {
            while (reader.ready()) {
                names.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String randomSelect(ArrayList<String> names) {
        return names.get((int)(random() * names.size()));
    }

    void showName(String firstName, String surname) {
        String fullName = getResources().getString(R.string.name_view,
                firstName, surname
        );
        ((TextView)findViewById(R.id.name)).setText(fullName);
    }

    String randomRace() {
        double r = random();
        int selection;
        if (r <= 0.5) {
            selection = R.string.human;
        } else if (r <= 0.7) {
            selection = R.string.elf;
        } else if (r <= 0.85) {
            selection = R.string.dwarf;
        } else {
            selection = R.string.halfling;
        }
        return getResources().getString(selection);
    }

    void showDetails(String gender, String race) {
        String fullName = getResources().getString(R.string.details_view,
                gender, race
        );
        ((TextView)findViewById(R.id.details)).setText(fullName);
    }

    public void randomMale(View view) {
        showName(randomSelect(maleNames), randomSelect(surnames));
        showDetails("Male", randomRace());
    }

    public void randomFemale(View view) {
        showName(randomSelect(femaleNames), randomSelect(surnames));
        showDetails("Female", randomRace());
    }

    public void randomName(View view) {
        if (random() <= 0.5) {
            randomMale(view);
        } else {
            randomFemale(view);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readNames(maleNames, R.raw.male);
        readNames(femaleNames, R.raw.female);
        readNames(surnames, R.raw.surname);

        randomName(null);
    }
}
