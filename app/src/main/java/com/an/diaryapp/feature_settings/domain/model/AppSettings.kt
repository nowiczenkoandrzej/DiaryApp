package com.an.diaryapp.feature_settings.domain.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Serializable
data class AppSettings(
    @Serializable(with = TimeSerializer::class)
    val notificationHour: LocalTime? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalTime::class)
object TimeSerializer: KSerializer<LocalTime> {


    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override val descriptor: SerialDescriptor
        get() = TODO("Not yet implemented")
    override fun deserialize(decoder: Decoder): LocalTime {
        return LocalTime.parse(decoder.decodeString(), formatter)
    }

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(value.format(formatter))
    }


}

