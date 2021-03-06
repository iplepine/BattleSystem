package com.zs.mol.view.adventure

import android.content.Context
import android.graphics.Canvas
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.util.Log
import android.view.TextureView
import com.zs.mol.view.adventure.viewmodel.GameScene

class GameTextureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextureView(context, attrs, defStyleAttr), TextureView.SurfaceTextureListener {

    lateinit var gameScene: GameScene
    var renderingThread: RenderingThread? = null
    var isRunning = true

    var lastTime = System.currentTimeMillis()

    init {
        surfaceTextureListener = this
        setOnTouchListener { v, event ->
            gameScene.onTouchEvent(event)
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        renderingThread?.isRunning = false
        // TODO 이거 조인 왜하지?
        try {
            renderingThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        renderingThread = RenderingThread(this, gameScene)
        renderingThread?.start()
    }

    class RenderingThread(val textureView: TextureView, val gameScene: GameScene) :
        Thread() {

        var isRunning = false
        var lastTime = System.currentTimeMillis()

        override fun run() {
            var canvas: Canvas?
            while (isRunning) {
                canvas = textureView.lockCanvas()
                try {
                    synchronized(textureView) {
                        val pastTime = System.currentTimeMillis() - lastTime
                        lastTime = System.currentTimeMillis()
                        gameScene.update(canvas, pastTime)

                        if (16 < pastTime) {
                            Log.e("WARNING", "TOO SLOW, time past $pastTime ms")
                        }
                    }
                } finally {
                    if (canvas == null) return
                    textureView.unlockCanvasAndPost(canvas)
                }
            }
        }

        override fun start() {
            super.start()
            isRunning = true
        }
    }
}