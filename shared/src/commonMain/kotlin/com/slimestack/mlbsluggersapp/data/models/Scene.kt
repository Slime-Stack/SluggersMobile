package com.slimestack.mlbsluggersapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Scene(
    val actNumber: Int,
    val sceneNumber: Int,
    val description: String,
    val imageUrl: String,
    @SerialName("audioUrl_en")
    val audioUrlEn: String,
    @SerialName("audioUrl_es")
    val audioUrlEs: String,
    @SerialName("audioUrl_ja")
    val audioUrlJa: String,
    @SerialName("caption_en")
    val captionEn: String,
    @SerialName("caption_es")
    val captionEs: String,
    @SerialName("caption_ja")
    val captionJa: String,
    val visualDescription: String,
    val imagenPrompt: String,
)
