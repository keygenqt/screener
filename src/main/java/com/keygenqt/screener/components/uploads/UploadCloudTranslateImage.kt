/*
 * Copyright 2020 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.keygenqt.screener.components.uploads

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate.TranslateOption
import com.google.cloud.translate.TranslateOptions
import com.keygenqt.screener.models.Settings
import java.io.File
import java.io.FileInputStream

class UploadCloudTranslateImage {
    companion object {
        fun translate(text: String): String {
            val credStream = FileInputStream(File(Settings.getSetting().credentials))
            val credentials = GoogleCredentials.fromStream(credStream)
            val translate = TranslateOptions.getDefaultInstance()
            val translation =
                translate.toBuilder().setCredentials(credentials).build().service.translate(
                    text,
                    TranslateOption.targetLanguage(Settings.getSetting().languageTranslate)
                )
            return translation.translatedText
        }
    }
}