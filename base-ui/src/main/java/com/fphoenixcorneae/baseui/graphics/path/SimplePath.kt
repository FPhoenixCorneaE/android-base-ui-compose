package com.fphoenixcorneae.baseui.graphics.path

import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin

/**
 * [Android 多边形导圆角（Path画折线导圆角）](https://blog.csdn.net/liuyu0915/article/details/131721872)
 */
class SimplePath {
    class Builder {
        private var mPath: Path? = null
        private val mList = mutableListOf<P>()

        private class P constructor(
            var x: Float,
            var y: Float,
            var startRadius: Float = 0f,
            var endRadius: Float = 0f,
        ) {
            /** 是否是起始点 */
            var isStartPoint = false

            fun hasCorner(): Boolean {
                return endRadius > 0 && startRadius > 0
            }
        }

        fun setPath(path: Path?): Builder {
            mPath = path
            return this
        }

        fun moveTo(x: Float, y: Float, startRadius: Float = 0f, endRadius: Float = 0f): Builder {
            val p = P(x, y, startRadius, endRadius)
            p.isStartPoint = true
            mList.add(0, p)
            return this
        }

        fun addRect(rect: Rect, r: Float): Builder {
            return addRect(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat(), r)
        }

        fun addRect(rect: RectF, r: Float): Builder {
            return addRect(rect.left, rect.top, rect.right, rect.bottom, r)
        }

        fun addRect(left: Float, top: Float, right: Float, bottom: Float, r: Float): Builder {
            moveTo(left, top, r, r)
            lineTo(right, top, r, r)
            lineTo(right, bottom, r, r)
            lineTo(left, bottom, r, r)
            close()
            return this
        }

        fun lineTo(x: Float, y: Float, startRadius: Float = 0f, endRadius: Float = 0f): Builder {
            mList.add(P(x, y, startRadius, endRadius))
            return this
        }

        fun close(): Builder {
            if (mList.size >= 1) {
                mList.add(mList[0])
            }
            return this
        }

        fun build(): Path {
            var path = mPath
            if (path == null) {
                path = Path()
            }
            for (i in mList.indices) {
                val p = mList[i]
                val x = p.x
                val y = p.y
                when (i) {
                    0 -> {
                        if (p.isStartPoint) {
                            if (p.hasCorner()) {
                                val p1 = mList[i + 1]
                                val onePoint = getOnLinePointLocationEnd(
                                    length = p.startRadius,
                                    x1 = p1.x,
                                    y1 = p1.y,
                                    x2 = p.x,
                                    y2 = p.y
                                )
                                path.moveTo(onePoint[0], onePoint[1])
                            } else {
                                path.moveTo(x, y)
                            }
                        } else {
                            path.lineTo(x, y)
                        }
                    }
                    mList.lastIndex -> {
                        // 最后一个点
                        val p0 = mList[0]
                        if (p.x == p0.x && p.y == p0.y && p0.hasCorner()) {
                            val p1 = mList[i - 1]
                            val p3 = mList[1]
                            lineToAndCorner(
                                path = path,
                                startRadius = p0.startRadius,
                                endRadius = p0.endRadius,
                                x1 = p1.x,
                                y1 = p1.y,
                                x2 = p0.x,
                                y2 = p0.y,
                                x3 = p3.x,
                                y3 = p3.y
                            )
                        } else {
                            path.lineTo(p.x, p.y)
                        }
                    }
                    else -> {
                        if (p.hasCorner()) {
                            val p1 = mList[i - 1]
                            val p3 = mList[i + 1]
                            lineToAndCorner(
                                path = path,
                                startRadius = p.startRadius,
                                endRadius = p.endRadius,
                                x1 = p1.x,
                                y1 = p1.y,
                                x2 = p.x,
                                y2 = p.y,
                                x3 = p3.x,
                                y3 = p3.y
                            )
                        } else {
                            path.lineTo(x, y)
                        }
                    }
                }
            }
            return path
        }

        /**
         * 画线并添加圆点 （绘制的线是起始点到圆角结束点的路径，并不包含到p3点路径）
         *
         * @param path
         * @param startRadius 起始圆角半径
         * @param endRadius   结束圆角半径
         * @param x1          起始点
         * @param y1
         * @param x2          中间点
         * @param y2
         * @param x3          结束点
         * @param y3
         */
        fun lineToAndCorner(
            path: Path,
            startRadius: Float,
            endRadius: Float,
            x1: Float,
            y1: Float,
            x2: Float,
            y2: Float,
            x3: Float,
            y3: Float,
        ) {
            val onePoint = getOnLinePointLocationEnd(startRadius, x1, y1, x2, y2)
            path.lineTo(onePoint[0], onePoint[1])
            val twoPoint = getOnLinePointLocationStart(endRadius, x2, y2, x3, y3)
            // 绘制圆角
            path.cubicTo(onePoint[0], onePoint[1], x2, y2, twoPoint[0], twoPoint[1])
        }

        /**
         * 获取线上点坐标
         *
         * @param length 线上点距离起始点(x1,y1）长度
         * @param x1     起始点x坐标
         * @param y1     起始点y坐标
         * @param x2     结束点x坐标
         * @param y2     结束点y坐标
         * @return
         */
        fun getOnLinePointLocationStart(length: Float, x1: Float, y1: Float, x2: Float, y2: Float): FloatArray {
            val degree = getDegree(x1, y1, x2, y2)
            val dx = getRightSideFromDegree(degree, length.toDouble())
            val dy = getLeftSideFromDegree(degree, length.toDouble())
            val v2 = x1 + dx
            val v3 = y1 + dy
            return floatArrayOf(v2.toFloat(), v3.toFloat())
        }

        /**
         * 获取线上点坐标
         *
         * @param length 线上点距离结束点（x2,y2）长度
         * @param x1     起始点x坐标
         * @param y1     起始点y坐标
         * @param x2     结束点x坐标
         * @param y2     结束点y坐标
         * @return
         */
        fun getOnLinePointLocationEnd(length: Float, x1: Float, y1: Float, x2: Float, y2: Float): FloatArray {
            val degree = getDegree(x1, y1, x2, y2)
            val dx = getRightSideFromDegree(degree, length.toDouble())
            val dy = getLeftSideFromDegree(degree, length.toDouble())
            val v2 = x2 - dx
            val v3 = y2 - dy
            return floatArrayOf(v2.toFloat(), v3.toFloat())
        }

        /**
         * 两点间的角度
         * @param sx
         * @param sy
         * @param tx
         * @param ty
         * @return
         */
        fun getDegree(sx: Float, sy: Float, tx: Float, ty: Float): Double {
            val nX = tx - sx
            val nY = ty - sy
            val angrad: Double
            val angle: Double
            val tpi: Double
            val tan: Float
            if (nX.compareTo(0.0f) != 0) {
                tan = abs(nY / nX)
                angle = atan(tan.toDouble())
                angrad = if (nX.compareTo(0.0f) == 1) {
                    if (nY.compareTo(0.0f) == 1 || nY.compareTo(0.0f) == 0) {
                        angle
                    } else {
                        2 * Math.PI - angle
                    }
                } else {
                    if (nY.compareTo(0.0f) == 1 || nY.compareTo(0.0f) == 0) {
                        Math.PI - angle
                    } else {
                        Math.PI + angle
                    }
                }
            } else {
                tpi = Math.PI / 2
                angrad = if (nY.compareTo(0.0f) == 1) {
                    tpi
                } else {
                    -1 * tpi
                }
            }
            return Math.toDegrees(angrad)
        }

        /**
         * 直角三角形 根据角度和斜边求直角边
         *
         * @param degree 角度
         * @param width  斜边
         * @return 直角边长
         */
        fun getRightSideFromDegree(degree: Double, width: Double): Double {
            val cos = cos(Math.toRadians(degree))
            return width * cos
        }

        fun getLeftSideFromDegree(degree: Double, width: Double): Double {
            val sin = sin(Math.toRadians(degree))
            return width * sin
        }
    }
}