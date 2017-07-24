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

import android.app.Application;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;


public class KutukApp extends Application {
    public final long SCHEMA_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration.Builder config = new RealmConfiguration.Builder()
                .name("kutuk.realm")
                .schemaVersion(SCHEMA_VERSION);
        if (BuildConfig.DEBUG) {
            config.deleteRealmIfMigrationNeeded();
        } else {
            config.migration(new Migration());
        }
        Realm.setDefaultConfiguration(config.build());
    }

    private class Migration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema schema = realm.getSchema();
        }
    }
}
