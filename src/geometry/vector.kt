package geometry

import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

data class vec4(val x: Float, val y: Float = x, val z: Float = y, val w: Float = 1f) {
    operator fun plus(v: vec4): vec4 = vec4(x + v.x, y + v.y, z + v.z, w + v.w)
    operator fun minus(v: vec4): vec4 = vec4(x - v.x, y - v.y, z - v.z, w - v.w)
    operator fun times(v: Float): vec4 = vec4(x * v, y * v, z * v, w * v)
    operator fun times(v: vec4) = vec4(x * v.x, y * v.y, z * v.z, w * v.w)
    operator fun div(v: Float): vec4 = vec4(x / v, y / v, z / v, w / v)
    operator fun div(v: vec4) = vec4(x / v.x, y / v.y, z / v.z, w / v.w)
    operator fun unaryMinus(): vec4 = vec4(-x, -y, -z, -w)
    override fun toString(): String = "($x, $y, $z, w=$w)"
}

data class vec3(val x: Float, val y: Float = x, val z: Float = y) {
    operator fun plus(v: vec3): vec3 = vec3(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: vec3): vec3 = vec3(x - v.x, y - v.y, z - v.z)
    operator fun times(v: Float): vec3 = vec3(x * v, y * v, z * v)
    operator fun times(v: vec3) = vec3(x * v.x, y * v.y, z * v.z)
    operator fun div(v: Float): vec3 = vec3(x / v, y / v, z / v)
    operator fun div(v: vec3) = vec3(x / v.x, y / v.y, z / v.z)
    operator fun unaryMinus(): vec3 = vec3(-x, -y, -z)
    override fun toString(): String = "($x, $y, $z)"
}

data class vec2(val x: Float, val y: Float = x) {
    operator fun plus(v: vec2): vec2 = vec2(x + v.x, y + v.y)
    operator fun minus(v: vec2): vec2 = vec2(x - v.x, y - v.y)
    operator fun times(v: Float): vec2 = vec2(x * v, y * v)
    operator fun times(v: vec2) = vec2(x * v.x, y * v.y)
    operator fun div(v: Float): vec2 = vec2(x / v, y / v)
    operator fun div(v: vec2) = vec2(x / v.x, y / v.y)
    operator fun unaryMinus(): vec2 = vec2(-x, -y)
    override fun toString(): String = "($x, $y)"
}

//////////////////////////////////////////////////////////////////////////////////////////

val vec2_zero = vec2(0f)
val vec2_one = vec2(1f)
val vec2_x = vec2(1f, 0f)
val vec2_y = vec2(0f, 1f)

val vec3_zero = vec3(0f)
val vec3_one = vec3(1f)
val vec3_x = vec3(1f, 0f, 0f)
val vec3_y = vec3(0f, 1f, 0f)
val vec3_z = vec3(0f, 0f, 1f)

//////////////////////////////////////////////////////////////////////////////////////////

fun vec4.vec3() = vec3(x, y, z)
fun vec3.vec2() = vec2(x, y)

fun vec3.vec4(w: Float) = vec4(x, y, z, w)
fun vec2.vec3(z: Float) = vec3(x, y, z)

//////////////////////////////////////////////////////////////////////////////////////////

fun vec2.length2() = x * x + y * y
fun vec3.length2() = x * x + y * y + z * z

fun vec2.length() = sqrt(length2())
fun vec3.length() = sqrt(length2())

fun vec2.normalise() = this / length()
fun vec3.normalise() = this / length()
fun vec2.normalise(length: Float) = this * length / length()
fun vec3.normalise(length: Float) = this * length / length()

infix fun vec2.dot(v: vec2) = x * v.x + y * v.y
infix fun vec3.dot(v: vec3) = x * v.x + y * v.y + z * v.z

infix fun vec3.cross(v: vec3) = vec3(
        y * v.z - z * v.y,
        z * v.x - x * v.z,
        x * v.y - y * v.x
)

//////////////////////////////////////////////////////////////////////////////////////////

fun vec2.unpack(): List<Float> = listOf(x, y)
fun vec3.unpack(): List<Float> = listOf(x, y, z)
fun vec4.unpack(): List<Float> = listOf(x, y, z, w)

fun vec2.min() = min(x, y)
fun vec3.min() = min(min(x, y), z)
fun vec4.min() = min(min(min(x, y), z), w)

fun vec2.max() = max(x, y)
fun vec3.max() = max(max(x, y), z)
fun vec4.max() = max(max(max(x, y), z), w)

//////////////////////////////////////////////////////////////////////////////////////////

fun vec2.rotate90CCW(): vec2 = vec2(-y, x)
fun vec2.rotate90CW(): vec2 = vec2(y, -x)
fun vec2.rotate45CCW(): vec2 = vec2(sqrt22 * x - sqrt22 * y,
                                    sqrt22 * y + sqrt22 * x)
fun vec2.rotate45CW(): vec2 = vec2(sqrt22 * x + sqrt22 * y,
                                    sqrt22 * y - sqrt22 * x)

fun vec3.toRotationMatrix(): mat3
        = mat3_rotate(y, vec3(0f, 1f, 0f)) *
        mat3_rotate(x, vec3(1f, 0f, 0f)) *
        mat3_rotate(z, vec3(0f, 0f, 1f))

fun vec3.toInverseRotationMatrix(): mat3
        = mat3_rotate(-z, vec3(0f, 0f, 1f)) *
        mat3_rotate(-x, vec3(1f, 0f, 0f)) *
        mat3_rotate(-y, vec3(0f, 1f, 0f))

private val sqrt22 = sqrt(2f)/2
