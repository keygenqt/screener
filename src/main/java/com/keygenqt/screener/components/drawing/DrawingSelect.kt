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

package com.keygenqt.screener.components.drawing

import com.keygenqt.screener.utils.COLOR_BG
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.SwingUtilities

class DrawingSelect(
    private val move: (DrawingSelect) -> Unit = {},
    private val save: (DrawingSelect) -> Unit = {},
    var rectangle: Rectangle? = null
) : MouseMotionListener, DrawingSelector, MouseListener {

    private lateinit var pointStart: Point
    private var enable: Boolean = true

    override fun mouseMoved(e: MouseEvent) {
        pointStart = e.point
    }

    override fun mouseDragged(e: MouseEvent) {
        if (enable && SwingUtilities.isLeftMouseButton(e)) {
            rectangle = mouseDragged(pointStart, e)
            move.invoke(DrawingSelect({}, {}, rectangle))
        }
    }

    override fun disable() {
        enable = false
    }

    override fun enable() {
        enable = true
    }

    override fun mouseReleased(p0: MouseEvent?) {
        if (enable && p0 != null && SwingUtilities.isLeftMouseButton(p0) && rectangle != null) {
            save.invoke(DrawingSelect({}, {}, rectangle))
        }
    }

    override fun mouseEntered(p0: MouseEvent?) {
    }

    override fun mouseClicked(p0: MouseEvent?) {
    }

    override fun mouseExited(p0: MouseEvent?) {
    }

    override fun mousePressed(p0: MouseEvent?) {
    }

    fun repaint(
        g: Graphics2D,
        rectangle: Rectangle?,
        width: Int,
        height: Int
    ) {
        rectangle?.let {
            g.color = COLOR_BG

            g.fill(Rectangle(0, 0, width, it.y))
            g.fill(Rectangle(0, it.y, it.x, it.height))
            g.fill(
                Rectangle(
                    it.x + it.width,
                    it.y,
                    width - (it.x + it.width),
                    it.height
                )
            )
            g.fill(
                Rectangle(
                    0,
                    it.height + it.y,
                    width,
                    height - (it.height + it.y)
                )
            )
        }
    }
}