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

import android.content.ComponentName;
import android.support.v7.widget.RecyclerView;

import me.kaangenc.ktk.data.Category;
import me.kaangenc.ktk.data.NamedRealmFactory;


public class CategoryActivity extends NamedListActivity {
    @Override protected RecyclerView.Adapter getViewAdapter() {
        return new NamedViewAdapter<>(
                realm.where(Category.class).findAll(),
                new ComponentName(
                        GameObjectActivity.class.getPackage().getName(),
                        GameObjectActivity.class.getName()
                )
        );
    }

    @Override protected NamedRealmFactory getRealmFactory() {
        return new Category.RealmFactory();
    }

    @Override protected String getListTitle() {
        return getString(R.string.category);
    }
}
