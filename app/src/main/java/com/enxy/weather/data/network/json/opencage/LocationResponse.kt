package com.enxy.weather.data.network.json.opencage

import com.google.gson.annotations.SerializedName


data class LocationResponse(
    @SerializedName("documentation")
    val documentation: String,
    @SerializedName("licenses")
    val licenses: List<License>,
    @SerializedName("rate")
    val rate: Rate,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("status")
    val status: Status,
    @SerializedName("stay_informed")
    val stayInformed: StayInformed,
    @SerializedName("thanks")
    val thanks: String,
    @SerializedName("timestamp")
    val timestamp: Timestamp,
    @SerializedName("total_results")
    val totalResults: Int
)

data class License(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Rate(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("remaining")
    val remaining: Int,
    @SerializedName("reset")
    val reset: Int
)

data class Result(
    @SerializedName("annotations")
    val annotations: Annotations,
    @SerializedName("bounds")
    val bounds: Bounds,
    @SerializedName("components")
    val components: Components,
    @SerializedName("confidence")
    val confidence: Int,
    @SerializedName("formatted")
    val formatted: String,
    @SerializedName("geometry")
    val geometry: Geometry
)

data class Annotations(
    @SerializedName("callingcode")
    val callingcode: Int,
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("DMS")
    val dMS: DMS,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("geohash")
    val geohash: String,
    @SerializedName("MGRS")
    val mGRS: String,
    @SerializedName("Maidenhead")
    val maidenhead: String,
    @SerializedName("Mercator")
    val mercator: Mercator,
    @SerializedName("OSM")
    val oSM: OSM,
    @SerializedName("qibla")
    val qibla: Double,
    @SerializedName("roadinfo")
    val roadinfo: Roadinfo,
    @SerializedName("sun")
    val sun: Sun,
    @SerializedName("timezone")
    val timezone: Timezone,
    @SerializedName("UN_M49")
    val uNM49: UNM49,
    @SerializedName("what3words")
    val what3words: What3words
)

data class Currency(
    @SerializedName("alternate_symbols")
    val alternateSymbols: List<Any>,
    @SerializedName("decimal_mark")
    val decimalMark: String,
    @SerializedName("html_entity")
    val htmlEntity: String,
    @SerializedName("iso_code")
    val isoCode: String,
    @SerializedName("iso_numeric")
    val isoNumeric: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("smallest_denomination")
    val smallestDenomination: Int,
    @SerializedName("subunit")
    val subunit: String,
    @SerializedName("subunit_to_unit")
    val subunitToUnit: Int,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("symbol_first")
    val symbolFirst: Int,
    @SerializedName("thousands_separator")
    val thousandsSeparator: String
)

data class DMS(
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String
)

data class Mercator(
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
)

data class OSM(
    @SerializedName("edit_url")
    val editUrl: String,
    @SerializedName("note_url")
    val noteUrl: String,
    @SerializedName("url")
    val url: String
)

data class Roadinfo(
    @SerializedName("drive_on")
    val driveOn: String,
    @SerializedName("road")
    val road: String,
    @SerializedName("speed_in")
    val speedIn: String
)

data class Sun(
    @SerializedName("rise")
    val rise: Rise,
    @SerializedName("set")
    val `set`: Set
)

data class Rise(
    @SerializedName("apparent")
    val apparent: Int,
    @SerializedName("astronomical")
    val astronomical: Int,
    @SerializedName("civil")
    val civil: Int,
    @SerializedName("nautical")
    val nautical: Int
)

data class Set(
    @SerializedName("apparent")
    val apparent: Int,
    @SerializedName("astronomical")
    val astronomical: Int,
    @SerializedName("civil")
    val civil: Int,
    @SerializedName("nautical")
    val nautical: Int
)

data class Timezone(
    @SerializedName("name")
    val name: String,
    @SerializedName("now_in_dst")
    val nowInDst: Int,
    @SerializedName("offset_sec")
    val offsetSec: Int,
    @SerializedName("offset_string")
    val offsetString: String,
    @SerializedName("short_name")
    val shortName: String
)

data class UNM49(
    @SerializedName("regions")
    val regions: Regions,
    @SerializedName("statistical_groupings")
    val statisticalGroupings: List<String>
)

data class Regions(
    @SerializedName("ASIA")
    val aSIA: String,
    @SerializedName("CENTRAL_ASIA")
    val cENTRALASIA: String,
    @SerializedName("KZ")
    val kZ: String,
    @SerializedName("WORLD")
    val wORLD: String
)

data class What3words(
    @SerializedName("words")
    val words: String
)

data class Bounds(
    @SerializedName("northeast")
    val northeast: Northeast,
    @SerializedName("southwest")
    val southwest: Southwest
)

data class Northeast(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

data class Southwest(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

data class Components(
    @SerializedName("_category")
    val category: String,
    @SerializedName("continent")
    val continent: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("county")
    val county: String,
    @SerializedName("ISO_3166-1_alpha-2")
    val iSO31661Alpha2: String,
    @SerializedName("ISO_3166-1_alpha-3")
    val iSO31661Alpha3: String,
    @SerializedName("neighbourhood")
    val neighbourhood: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("restaurant")
    val restaurant: String,
    @SerializedName("road")
    val road: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("town")
    val town: String,
    @SerializedName("_type")
    val type: String
)

data class Geometry(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)

data class StayInformed(
    @SerializedName("blog")
    val blog: String,
    @SerializedName("twitter")
    val twitter: String
)

data class Timestamp(
    @SerializedName("created_http")
    val createdHttp: String,
    @SerializedName("created_unix")
    val createdUnix: Int
)