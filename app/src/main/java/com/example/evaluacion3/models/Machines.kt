package com.example.evaluacion3.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Machines(
    var id: String? = null,
    var name: String? = null,
    var product: String? = null,
    var temperature: String? = null,
) : Parcelable
