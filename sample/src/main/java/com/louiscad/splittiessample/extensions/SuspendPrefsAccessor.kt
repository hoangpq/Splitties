/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.louiscad.splittiessample.extensions

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.withContext
import splitties.preferences.Preferences

abstract class SuspendPrefsAccessor<out Prefs : Preferences>(
        private val initDispatcher: CoroutineDispatcher,
        prefsConstructorRef: () -> Prefs
) {
    constructor(createPrefs: () -> Prefs) : this(CommonPool, createPrefs)

    init {
        require(initDispatcher !== UI) {
            "Instantiating SharedPreferences performs I/O, which is not allowed on UI thread."
        }
    }

    suspend operator fun invoke(): Prefs {
        return if (prefDelegate.isInitialized()) prefs else withContext(initDispatcher) { prefs }
    }

    private val prefDelegate = lazy(prefsConstructorRef)
    private val prefs by prefDelegate
}
