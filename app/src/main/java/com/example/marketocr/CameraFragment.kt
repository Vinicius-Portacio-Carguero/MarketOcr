package com.example.marketocr

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.marketocr.utils.alert.Alert
import com.example.marketocr.utils.spinners.SpinnerSetup
import com.example.marketocr.viewModel.ValueViewModel
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer

class CameraFragment : Fragment(), View.OnClickListener{

    // TODO: Criar módulo de permissão
    private val permission = com.example.marketocr.utils.camera.Permission(context)

    private val viewModel: ValueViewModel by viewModels()
    private val spinnerList = mutableListOf<Int>()
    private val saveList = mutableListOf<String>()

    private val alert = Alert()
    private val spinnerSetup = SpinnerSetup()

    private var value = view?.findViewById<TextView>(R.id.tv_result)
    private var spinner = view?.findViewById<Spinner>(R.id.spinner)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater
            .inflate(R
                .layout
                .fragment_camera,
                container,
                false)

        for( i in 0..20){ spinnerList.add(i) }

        spinnerSetup.spinnerSetup(
            view?.findViewById(R.id.spinner),
            spinnerList,
            requireContext()
        )

        var resultText = view.findViewById<TextView>(R.id.tv_result)

        view.findViewById<Button>(R.id.button_save).setOnClickListener {
//            saveList.add(resultText.text.toString())
//            println(saveList)
//            showSaveDialog(saveList[0])

            saveList.add(spinnerList[spinner?.selectedItemPosition!!].toString())
            showSaveDialog(saveList[0])
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        activity?.let { permission.requestPermissionCamera(context, it) }
        permission.permissionCheck(context)

        doOcr(view?.findViewById(R.id.surface_camera_preview))

    }

    private fun doOcr(surfaceView: SurfaceView?){

        val ocr = TextRecognizer.Builder(context).build()

        if(!ocr.isOperational){
            Toast.makeText(context, "Baixando dependências, por favor, tente novamente em instantes", Toast.LENGTH_LONG)
            return
        }

        val mCameraSource = CameraSource.Builder(context, ocr)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1290, 1024)
            .setAutoFocusEnabled(true)
            .setRequestedFps(2.0f)
            .build()

        surfaceView?.holder?.addCallback(object : SurfaceHolder.Callback{

            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                 try{
                    if(permission.permissionCheck(context) == 0){
                            mCameraSource.start(surfaceView.holder)
                        } else{
                            activity?.let { permission.requestPermissionCamera(context, it) }
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) { mCameraSource.stop() }

        })

        ocr.setProcessor(object: Detector.Processor<TextBlock>{
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<TextBlock>?) {

                val items = detections?.detectedItems

                if(items?.size()!! <= 0){
                    return
                }

                val resultTextView = view?.findViewById<TextView>(R.id.tv_result)

                resultTextView?.post {
                    val stringBuilder = StringBuilder()
                    for (i in 0 until items.size()) {
                        val item = items.valueAt(i)
                        stringBuilder.append(item.value)
                        stringBuilder.append("\n")
                    }

                    resultTextView?.text = stringBuilder.toString()

                }
            }
        })
    }




    private fun showSaveDialog(message: String){
        val showDialog = alert.showDialog("Deseja salvar esse valor ?", context)
            .setMessage(message)
            .setPositiveButton(R.string.YES_DIALOG){dialog, which -> /*** save() ***/}
            .setNegativeButton(R.string.NOT_DIALOG) { dialog, which ->
                Toast.makeText(context, "Valor não salvo", Toast.LENGTH_SHORT)
            }
            .create()

        showDialog.show()
    }

    private fun showValuePreview(title: String, context: Context?){
        val result = viewModel.sumAll(context)

        val createDialog = alert
            .showDialog(result, context)
            .setPositiveButton(R.string.OK_DIALOG){ dialog, which -> }
            .create()

            createDialog.show()
    }

    private fun save(value: String, quantity: Int?, context: Context?){
        viewModel
            .doInsertValue(value.replace(",", "."), quantity, context)

        for(i in 0..saveList.size - 1) saveList.removeFirst()

    }

    override fun onClick(v: View?) {}
}


