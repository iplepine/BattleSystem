package com.zs.mol.view.adventure

import android.content.Context
import android.graphics.Canvas
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.TextureView
import com.zs.mol.view.adventure.viewmodel.GameSceneViewModel

class GameTextureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextureView(context, attrs, defStyleAttr), TextureView.SurfaceTextureListener {

    lateinit var gameSceneViewModel: GameSceneViewModel
    var renderingThread : SurfaceRenderingThread? = null

    init {
        surfaceTextureListener = this
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
        } catch(e: InterruptedException) {
            e.printStackTrace()
        }
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        renderingThread = SurfaceRenderingThread(this, gameSceneViewModel)
        renderingThread?.start()
    }

    class SurfaceRenderingThread(val textureView: TextureView, val gameSceneViewModel: GameSceneViewModel)
        : Thread() {

        var isRunning = false

        override fun run() {
            var canvas: Canvas?
            while (isRunning) {
                canvas = textureView.lockCanvas()
                try {
                    synchronized(textureView) {
                        gameSceneViewModel.draw(canvas)
                    }
                } finally {
                    if(canvas == null) return
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