package com.an.diaryapp.feature_weather_api.data

import com.an.diaryapp.feature_weather_api.KtorResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
@SerialName("KtorResponse")
private data class WeatherSurrogate(
    val temperature_2m: Double,
    val weather_code: Long
)
object WeatherSerializer: KSerializer<KtorResponse> {
    override val descriptor: SerialDescriptor =
        WeatherSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): KtorResponse {
        val surrogate = decoder.decodeSerializableValue(WeatherSurrogate.serializer())
        return KtorResponse(current = KtorResponse.Current(
            temperature_2m = surrogate.temperature_2m,
            weather_code = surrogate.weather_code
        ))
    }

    override fun serialize(encoder: Encoder, value: KtorResponse) {
        val surrogate = WeatherSurrogate(
            temperature_2m = value.current.temperature_2m,
            weather_code = value.current.weather_code
        )
        encoder.encodeSerializableValue(WeatherSurrogate.serializer(), surrogate)
    }
}