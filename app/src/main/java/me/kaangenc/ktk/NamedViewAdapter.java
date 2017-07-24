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

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmObject;
import io.realm.RealmRecyclerViewAdapter;
import me.kaangenc.ktk.data.Named;

class NamedViewAdapter<D extends RealmObject & Named> extends RealmRecyclerViewAdapter<D, NamedViewAdapter.ViewHolder> {
    NamedViewAdapter(@Nullable OrderedRealmCollection<D> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public NamedViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.named_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NamedViewAdapter.ViewHolder holder, int position) {
        final Named named = getItem(position);
        holder.name.setText(named != null ? named.getName() : "");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }
    }
}
